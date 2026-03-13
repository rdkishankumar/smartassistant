package com.inchbyinch.smartassistant.data.loader;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.List;

//@Component
public class RandomDataLoader {
    private final VectorStore vectorStore;

    public RandomDataLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void loadDataIntoVectorStore() {

        List<String> sentences = List.of(

                "RBI Master Direction: Lending to Micro Small and Medium Enterprises MSME Sector was issued on July 24, 2017 under the Banking Regulation Act 1949 sections 21 and 35A.",

                "The RBI Master Direction on MSME lending consolidates guidelines issued to scheduled commercial banks regarding financing of Micro Small and Medium Enterprises.",

                "The MSME lending directions apply to all Scheduled Commercial Banks excluding Regional Rural Banks.",

                "The MSMED Act 2006 defines Micro Small and Medium Enterprises and provides the legal framework for MSME classification and development.",

                "Priority Sector Lending includes MSME financing as defined in RBI Priority Sector Lending Targets and Classification Directions.",

                "Adjusted Net Bank Credit ANBC is used as a reference base for calculating priority sector lending targets.",

                "A micro enterprise is defined as an enterprise where investment in plant and machinery does not exceed 2.5 crore rupees and turnover does not exceed 10 crore rupees.",

                "A small enterprise is defined as an enterprise where investment in plant and machinery does not exceed 25 crore rupees and turnover does not exceed 100 crore rupees.",

                "A medium enterprise is defined as an enterprise where investment in plant and machinery does not exceed 125 crore rupees and turnover does not exceed 500 crore rupees.",

                "MSME enterprises must register on the Udyam Registration portal to obtain the Udyam Registration Certificate.",

                "Banks use the classification recorded in the Udyam Registration Certificate for Priority Sector Lending purposes.",

                "Retail and wholesale trade businesses are included as MSMEs only for the limited purpose of Priority Sector Lending classification.",

                "Informal Micro Enterprises with Udyam Assist Portal certificates are treated as micro enterprises for priority sector lending purposes.",

                "Banks must follow priority sector lending targets and sub targets for MSME sector as specified by RBI.",

                "Banks should achieve 20 percent year on year credit growth for micro and small enterprises.",

                "Banks should ensure 10 percent annual growth in the number of micro enterprise accounts.",

                "At least 60 percent of total lending to the Micro and Small Enterprise sector should go to micro enterprises.",

                "Banks must not take collateral security for loans up to 20 lakh rupees given to Micro and Small Enterprises.",

                "Banks may extend collateral free loans up to 20 lakh rupees for units financed under the Prime Minister Employment Generation Programme PMEGP.",

                "Banks may increase the collateral free loan limit to 25 lakh rupees based on the financial position and track record of the borrower.",

                "Banks may use the Credit Guarantee Scheme for MSME loans to mitigate credit risk.",

                "A composite loan up to 1 crore rupees may be provided to MSE entrepreneurs covering both working capital and term loan requirements.",

                "Banks may issue General Credit Cards GCC to individuals and entities for non farm entrepreneurial activities classified under priority sector lending.",

                "Banks must streamline the credit flow to Micro and Small Enterprises during their business life cycle to provide timely financial support.",

                "Banks should provide standby credit facilities and additional working capital during financial stress situations for MSMEs.",

                "Banks must review working capital limits annually based on the previous year's sales performance.",

                "Banks should decide on MSME loan applications up to 25 lakh rupees within 14 working days.",

                "Banks must publish MSME loan guidelines, timelines and document checklists on their official websites.",

                "Banks must follow RBI prudential norms related to income recognition asset classification and provisioning when restructuring MSME loans.",

                "Banks should implement One Time Settlement schemes for recovery of non performing loans in the MSME sector.",

                "The Framework for Revival and Rehabilitation of MSMEs provides a mechanism to support stressed MSME accounts.",

                "Banks must identify early signs of financial stress in MSME accounts using Special Mention Account SMA classification.",

                "MSME borrowers may voluntarily request restructuring or rehabilitation under the MSME revival framework.",

                "A committee approach is recommended to decide corrective action plans for stressed MSME accounts.",

                "Banks must establish a Credit Proposal Tracking System to track MSME loan applications.",

                "The Credit Proposal Tracking System should generate acknowledgement with a unique application number for each MSME loan application.",

                "Banks must provide MSME borrowers with a checklist of required documents during loan application.",

                "Banks should monitor loan application disposal and report delays beyond approved sanction timelines.",

                "Banks must communicate reasons for rejection of MSME loan applications to the borrower.",

                "Banks must implement a comprehensive Management Information System MIS to monitor MSME credit performance.",

                "Public sector banks should open at least one specialised MSME branch in each district.",

                "General banking branches with more than 60 percent MSME advances may be classified as specialised MSME branches.",

                "Specialised MSME branches aim to improve access to credit and develop expertise in MSME financing.",

                "Empowered Committees on MSMEs are formed at RBI regional offices to review MSME credit growth and rehabilitation.",

                "The committee includes representatives from banks, SIDBI, state government and MSME associations.",

                "The Banking Codes and Standards Board of India created a Code of Bank Commitment to Micro and Small Enterprises to promote transparency and fair practices.",

                "Banks must promote financial literacy among MSME entrepreneurs to improve financial inclusion.",

                "Banks may set up financial literacy centres to support MSME borrowers with accounting, finance and business planning knowledge.",

                "A cluster approach is recommended where banks identify MSME clusters and provide targeted credit support.",

                "Lead banks should assess credit requirements of MSME units in clusters and facilitate credit linkages.",

                "Banks must create awareness among MSMEs about formal credit channels through financial literacy programs.",

                "Banks should include MSME cluster credit needs in district credit plans and annual credit plans.",

                "Under the MSMED Act buyers must pay MSME suppliers within the agreed period not exceeding 45 days.",

                "If payment to MSME suppliers is delayed the buyer must pay compound interest at three times the RBI bank rate.",

                "Disputes related to delayed payments may be referred to the Micro and Small Enterprises Facilitation Council.",

                "Banks should allocate sub limits within working capital limits for large borrowers to ensure timely payments to MSME suppliers.",

                "The Nayak Committee recommended measures to improve institutional credit flow to the small scale industry sector.",

                "The Ganguly Committee provided recommendations to enhance credit flow to the small and medium enterprise sector.",

                "The Chakrabarty Committee recommended using scoring models for lending decisions for MSME loans up to two crore rupees.",

                "The Prime Minister Task Force on MSMEs recommended measures to improve credit, marketing, infrastructure and skill development for MSMEs.",

                "Banks are encouraged to implement the recommendations of the Prime Minister Task Force to improve credit flow to micro enterprises."
        );

        List<Document> documents = sentences.stream().map(Document::new).toList();
        vectorStore.add(documents);

    }
}
