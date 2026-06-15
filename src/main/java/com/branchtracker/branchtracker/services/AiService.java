package com.branchtracker.branchtracker.services;


import com.branchtracker.branchtracker.entity.Loan;
import com.branchtracker.branchtracker.enums.LoanStatus;
import com.branchtracker.branchtracker.repositories.LoanRepository;
import com.branchtracker.branchtracker.repositories.PaymentRepository;
import com.branchtracker.branchtracker.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiService {

    @Value("${anthropic.api.key}")
    private String apiKey;

    private final LoanRepository loanRepository;
    private final PaymentRepository paymentRepository;
    private final ClientRepository clientRepository;

    private final org.springframework.web.client.RestTemplate restTemplate =
            new org.springframework.web.client.RestTemplate();

    // Generate a branch performance report using AI
    public String generateBranchReport(Long branchId) {

        // Gather data from the database
        long totalLoans = loanRepository.findByBranchId(branchId).size();
        long activeLoans = loanRepository.countByBranchIdAndStatus(branchId, LoanStatus.ACTIVE);
        long defaultedLoans = loanRepository.countByBranchIdAndStatus(branchId, LoanStatus.DEFAULTED);
        long completedLoans = loanRepository.countByBranchIdAndStatus(branchId, LoanStatus.COMPLETED);
        long totalClients = clientRepository.countByBranchId(branchId);

        BigDecimal outstandingBalance = loanRepository.getTotalOutstandingBalanceByBranch(branchId);
        BigDecimal totalCollected = paymentRepository.getTotalCollectedByBranch(branchId);

        // Build a prompt for Claude
        String prompt = String.format("""
                You are a financial analyst for a microfinance company in Uganda.
                Analyse the following branch performance data and provide a clear,
                concise report in 3-4 sentences that a branch manager can understand.
                Include key insights, concerns if any, and one recommendation.
                
                Branch ID: %d
                Total Clients: %d
                Total Loans: %d
                Active Loans: %d
                Completed Loans: %d
                Defaulted Loans: %d
                Total Outstanding Balance: UGX %s
                Total Amount Collected: UGX %s
                """,
                branchId, totalClients, totalLoans, activeLoans,
                completedLoans, defaultedLoans,
                outstandingBalance != null ? outstandingBalance.toPlainString() : "0",
                totalCollected != null ? totalCollected.toPlainString() : "0"
        );

        return callClaudeApi(prompt);
    }

    // Generate a loan risk assessment using AI
    public String assessLoanRisk(Long clientId) {

        List<Loan> clientLoans = loanRepository.findByClientId(clientId);

        long totalLoans = clientLoans.size();
        long completedLoans = clientLoans.stream()
                .filter(l -> l.getStatus() == LoanStatus.COMPLETED).count();
        long defaultedLoans = clientLoans.stream()
                .filter(l -> l.getStatus() == LoanStatus.DEFAULTED).count();
        long activeLoans = clientLoans.stream()
                .filter(l -> l.getStatus() == LoanStatus.ACTIVE).count();

        String prompt = String.format("""
                You are a loan risk analyst for a microfinance company in Uganda.
                Based on the following client loan history, provide a risk assessment.
                Give a risk level (LOW, MEDIUM, HIGH) and a 2-3 sentence explanation.
                
                Client ID: %d
                Total Loans Taken: %d
                Completed (Fully Repaid): %d
                Currently Active: %d
                Defaulted: %d
                """,
                clientId, totalLoans, completedLoans, activeLoans, defaultedLoans
        );

        return callClaudeApi(prompt);
    }

    // Generate overall company financial summary
    public String generateCompanySummary() {

        BigDecimal totalOutstanding = loanRepository.getTotalOutstandingBalance();
        BigDecimal totalCollected = paymentRepository.getTotalCollected();

        String prompt = String.format("""
                You are a financial analyst for a microfinance company in Uganda.
                Provide a brief executive summary of the company's financial position
                in 3-4 sentences. Include the financial health status and one strategic recommendation.
                
                Total Outstanding Loan Balance: UGX %s
                Total Amount Collected from Repayments: UGX %s
                """,
                totalOutstanding != null ? totalOutstanding.toPlainString() : "0",
                totalCollected != null ? totalCollected.toPlainString() : "0"
        );

        return callClaudeApi(prompt);
    }

    // Call the Claude API
    private String callClaudeApi(String prompt) {
        try {
            // Set headers
            org.springframework.http.HttpHeaders headers =
                    new org.springframework.http.HttpHeaders();
            headers.set("x-api-key", apiKey);
            headers.set("anthropic-version", "2023-06-01");
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

            // Build request body
            Map<String, Object> message = new java.util.HashMap<>();
            message.put("role", "user");
            message.put("content", prompt);

            Map<String, Object> requestBody = new java.util.HashMap<>();
            requestBody.put("model", "claude-haiku-4-5-20251001");
            requestBody.put("max_tokens", 1024);
            requestBody.put("messages", List.of(message));

            org.springframework.http.HttpEntity<Map<String, Object>> entity =
                    new org.springframework.http.HttpEntity<>(requestBody, headers);

            Map response = restTemplate.postForObject(
                    "https://api.anthropic.com/v1/messages",
                    entity,
                    Map.class
            );

            if (response != null && response.containsKey("content")) {
                List<Map<String, Object>> content = (List<Map<String, Object>>) response.get("content");
                if (!content.isEmpty()) {
                    return (String) content.get(0).get("text");
                }
            }
            return "Unable to generate AI report at this time.";

        } catch (Exception e) {
            return "AI service error: " + e.getMessage();
        }
    }
}