import java.util.Scanner;
import java.util.ArrayList;

/**
 * Resume Analyzer - Beginner Java Project
 * This program analyzes a resume by checking for important keywords,
 * counting skills, and giving a score with suggestions.
 */
public class ResumeAnalyzer {

    // ===================== MAIN METHOD =====================
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("==========================================");
        System.out.println("       WELCOME TO RESUME ANALYZER        ");
        System.out.println("==========================================");
        System.out.println();

        // Step 1: Get the job role from user
        System.out.print("Enter the job role you are applying for: ");
        String jobRole = scanner.nextLine();

        // Step 2: Get resume text from user
        System.out.println();
        System.out.println("Paste your resume text below.");
        System.out.println("(Type END on a new line when done)");
        System.out.println("------------------------------------------");

        // Read multiple lines of resume text
        StringBuilder resumeText = new StringBuilder();
        String line = "";
        while (!(line = scanner.nextLine()).equals("END")) {
            resumeText.append(line).append(" ");
        }

        String resume = resumeText.toString().toLowerCase();

        System.out.println();
        System.out.println("==========================================");
        System.out.println("           ANALYZING YOUR RESUME...      ");
        System.out.println("==========================================");
        System.out.println();

        // Step 3: Run all analysis functions
        int totalScore = 0;

        totalScore += checkContactInfo(resume);
        totalScore += checkEducation(resume);
        totalScore += checkSkills(resume, jobRole.toLowerCase());
        totalScore += checkExperience(resume);
        totalScore += checkKeywords(resume);

        // Step 4: Show final score
        showFinalScore(totalScore);

        scanner.close();
    }

    // ===================== SECTION 1: CONTACT INFO =====================
    public static int checkContactInfo(String resume) {
        System.out.println("--- [1] Contact Information Check ---");
        int score = 0;

        // Check for email
        if (resume.contains("@") && resume.contains(".com")) {
            System.out.println("  ✔ Email found");
            score += 5;
        } else {
            System.out.println("  ✘ Email NOT found - Please add your email address");
        }

        // Check for phone number (simple check - looks for digits)
        if (resume.matches(".*\\d{3}.*\\d{3}.*\\d{4}.*") || resume.contains("phone") || resume.contains("mobile")) {
            System.out.println("  ✔ Phone number found");
            score += 5;
        } else {
            System.out.println("  ✘ Phone number NOT found - Please add your phone number");
        }

        // Check for LinkedIn
        if (resume.contains("linkedin")) {
            System.out.println("  ✔ LinkedIn profile found");
            score += 5;
        } else {
            System.out.println("  ✘ LinkedIn NOT found - Consider adding your LinkedIn profile");
        }

        System.out.println("  Contact Score: " + score + "/15");
        System.out.println();
        return score;
    }

    // ===================== SECTION 2: EDUCATION =====================
    public static int checkEducation(String resume) {
        System.out.println("--- [2] Education Section Check ---");
        int score = 0;

        // Check for degree keywords
        String[] degrees = {"bachelor", "master", "phd", "b.tech", "b.e", "mba", "diploma", "degree", "university", "college"};

        for (String degree : degrees) {
            if (resume.contains(degree)) {
                System.out.println("  ✔ Education found: '" + degree + "'");
                score += 10;
                break; // Only count once
            }
        }

        if (score == 0) {
            System.out.println("  ✘ No Education section found - Please add your educational background");
        }

        // Check for GPA/percentage
        if (resume.contains("gpa") || resume.contains("cgpa") || resume.contains("%") || resume.contains("percentage")) {
            System.out.println("  ✔ Academic score (GPA/%) found");
            score += 5;
        } else {
            System.out.println("  ✘ No GPA/Percentage mentioned - Consider adding it if it's strong");
        }

        System.out.println("  Education Score: " + score + "/15");
        System.out.println();
        return score;
    }

    // ===================== SECTION 3: SKILLS =====================
    public static int checkSkills(String resume, String jobRole) {
        System.out.println("--- [3] Skills Section Check ---");
        int score = 0;

        // General technical skills
        String[] generalSkills = {"java", "python", "sql", "html", "css", "javascript", "c++", "excel", "git", "linux"};
        ArrayList<String> foundSkills = new ArrayList<>();

        for (String skill : generalSkills) {
            if (resume.contains(skill)) {
                foundSkills.add(skill);
            }
        }

        if (!foundSkills.isEmpty()) {
            System.out.println("  ✔ Technical skills found: " + foundSkills);
            score += foundSkills.size() * 3; // 3 points per skill
        } else {
            System.out.println("  ✘ No technical skills detected - List your technical skills clearly");
        }

        // Job-role specific keyword check
        System.out.println();
        System.out.println("  Checking skills for role: '" + jobRole + "'");

        if (jobRole.contains("developer") || jobRole.contains("software") || jobRole.contains("engineer")) {
            String[] devSkills = {"java", "python", "data structures", "algorithms", "spring", "database"};
            checkRoleSkills(resume, devSkills, "Software Developer");

        } else if (jobRole.contains("data") || jobRole.contains("analyst")) {
            String[] dataSkills = {"python", "sql", "excel", "tableau", "machine learning", "statistics"};
            checkRoleSkills(resume, dataSkills, "Data Analyst");

        } else if (jobRole.contains("web") || jobRole.contains("frontend")) {
            String[] webSkills = {"html", "css", "javascript", "react", "bootstrap", "responsive"};
            checkRoleSkills(resume, webSkills, "Web Developer");

        } else {
            String[] commonSkills = {"communication", "teamwork", "leadership", "problem solving", "management"};
            checkRoleSkills(resume, commonSkills, jobRole);
        }

        // Cap score at 30
        if (score > 30) score = 30;

        System.out.println("  Skills Score: " + score + "/30");
        System.out.println();
        return score;
    }

    // Helper method to check role-specific skills
    public static void checkRoleSkills(String resume, String[] skills, String roleName) {
        System.out.println("  Role-specific skills for '" + roleName + "':");
        for (String skill : skills) {
            if (resume.contains(skill)) {
                System.out.println("    ✔ " + skill);
            } else {
                System.out.println("    ✘ " + skill + " (missing)");
            }
        }
    }

    // ===================== SECTION 4: EXPERIENCE =====================
    public static int checkExperience(String resume) {
        System.out.println("--- [4] Work Experience Check ---");
        int score = 0;

        // Check for experience keywords
        String[] expKeywords = {"experience", "worked", "internship", "project", "developed", "designed", "built", "implemented"};

        int count = 0;
        for (String keyword : expKeywords) {
            if (resume.contains(keyword)) {
                count++;
            }
        }

        if (count >= 3) {
            System.out.println("  ✔ Strong experience section detected (" + count + " experience keywords found)");
            score = 20;
        } else if (count >= 1) {
            System.out.println("  ✔ Some experience mentioned (" + count + " keyword(s) found)");
            score = 10;
        } else {
            System.out.println("  ✘ No experience section found - Add internships, projects, or work history");
        }

        // Check for action verbs
        String[] actionVerbs = {"managed", "led", "created", "improved", "analyzed", "coordinated", "achieved"};
        ArrayList<String> foundVerbs = new ArrayList<>();

        for (String verb : actionVerbs) {
            if (resume.contains(verb)) {
                foundVerbs.add(verb);
            }
        }

        if (!foundVerbs.isEmpty()) {
            System.out.println("  ✔ Action verbs found: " + foundVerbs);
            score += 5;
        } else {
            System.out.println("  ✘ Use more action verbs like: managed, created, improved, led");
        }

        System.out.println("  Experience Score: " + score + "/25");
        System.out.println();
        return score;
    }

    // ===================== SECTION 5: KEYWORDS =====================
    public static int checkKeywords(String resume) {
        System.out.println("--- [5] General Resume Keywords ---");
        int score = 0;

        // Professional keywords
        String[] keywords = {"objective", "summary", "achievements", "certification", "awards",
                             "volunteer", "references", "languages", "hobbies", "interests"};

        for (String keyword : keywords) {
            if (resume.contains(keyword)) {
                System.out.println("  ✔ Found: '" + keyword + "'");
                score += 1;
            }
        }

        // Bonus: Check resume length (word count)
        String[] words = resume.split("\\s+");
        int wordCount = words.length;

        System.out.println();
        System.out.println("  Word Count: " + wordCount + " words");

        if (wordCount >= 300 && wordCount <= 700) {
            System.out.println("  ✔ Good resume length (300-700 words recommended)");
            score += 5;
        } else if (wordCount < 300) {
            System.out.println("  ✘ Resume too short - Add more details about your experience and skills");
        } else {
            System.out.println("  ✘ Resume too long - Try to keep it under 700 words (1 page)");
        }

        System.out.println("  Keywords Score: " + score + "/15");
        System.out.println();
        return score;
    }

    // ===================== FINAL SCORE =====================
    public static void showFinalScore(int totalScore) {
        System.out.println("==========================================");
        System.out.println("           RESUME ANALYSIS RESULT        ");
        System.out.println("==========================================");
        System.out.println();
        System.out.println("  TOTAL SCORE: " + totalScore + " / 100");
        System.out.println();

        // Give rating based on score
        if (totalScore >= 80) {
            System.out.println("  RATING: ⭐⭐⭐⭐⭐ EXCELLENT!");
            System.out.println("  Your resume is very strong. Great job!");

        } else if (totalScore >= 60) {
            System.out.println("  RATING: ⭐⭐⭐⭐ GOOD");
            System.out.println("  Your resume is good. A few improvements can make it excellent!");

        } else if (totalScore >= 40) {
            System.out.println("  RATING: ⭐⭐⭐ AVERAGE");
            System.out.println("  Your resume needs work. Review the suggestions above.");

        } else if (totalScore >= 20) {
            System.out.println("  RATING: ⭐⭐ BELOW AVERAGE");
            System.out.println("  Your resume needs significant improvement. Follow the suggestions.");

        } else {
            System.out.println("  RATING: ⭐ NEEDS MAJOR WORK");
            System.out.println("  Please rebuild your resume with all sections properly filled.");
        }

        System.out.println();
        System.out.println("------------------------------------------");
        System.out.println("  TOP TIPS TO IMPROVE YOUR RESUME:");
        System.out.println("  1. Add all contact details (email, phone, LinkedIn)");
        System.out.println("  2. Include your education with GPA/percentage");
        System.out.println("  3. List relevant technical and soft skills");
        System.out.println("  4. Add internships, projects, or work experience");
        System.out.println("  5. Use strong action verbs (led, built, designed)");
        System.out.println("  6. Keep resume to 1 page (300-700 words)");
        System.out.println("------------------------------------------");
        System.out.println();
        System.out.println("  Thank you for using Resume Analyzer!");
        System.out.println("==========================================");
    }
}