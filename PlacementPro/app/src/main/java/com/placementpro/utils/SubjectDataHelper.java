package com.placementpro.utils;

import com.placementpro.models.Subject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubjectDataHelper {

    public static List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();

        // Core CS
        subjects.add(new Subject("dsa", "Data Structures & Algorithms", "Core CS",
                "#FF6B6B", "🔴",
                Arrays.asList("Arrays & Strings", "Linked Lists", "Stacks & Queues",
                        "Trees & BST", "Graphs & BFS/DFS", "Dynamic Programming",
                        "Greedy Algorithms", "Recursion & Backtracking",
                        "Sorting & Searching", "Hashing", "Heaps & Priority Queue",
                        "Tries", "Segment Trees", "Two Pointers", "Sliding Window"),
                "Most important for coding rounds. Practice daily on LeetCode.",
                95));

        subjects.add(new Subject("dbms", "Database Management System", "Core CS",
                "#4ECDC4", "🟢",
                Arrays.asList("ER Diagrams", "Normalization (1NF-BCNF)", "SQL Queries",
                        "Joins & Subqueries", "Transactions & ACID", "Concurrency Control",
                        "Indexing & B-Trees", "Recovery & Logging", "NoSQL Basics",
                        "Stored Procedures", "Triggers", "Views"),
                "Frequently asked in technical interviews. Focus on SQL.",
                85));

        subjects.add(new Subject("os", "Operating Systems", "Core CS",
                "#45B7D1", "🔵",
                Arrays.asList("Process Management", "CPU Scheduling", "Memory Management",
                        "Virtual Memory & Paging", "Deadlocks", "Synchronization",
                        "Semaphores & Mutex", "File Systems", "I/O Systems",
                        "Inter Process Communication", "Threads", "System Calls"),
                "Core concept for system-level interviews.",
                80));

        subjects.add(new Subject("cn", "Computer Networks", "Core CS",
                "#96CEB4", "🟡",
                Arrays.asList("OSI Model", "TCP/IP Protocol", "HTTP/HTTPS", "DNS",
                        "Routing Algorithms", "TCP vs UDP", "Subnetting",
                        "Application Layer Protocols", "Network Security",
                        "Sockets", "Firewalls", "CDN & Load Balancing"),
                "Important for backend and network engineering roles.",
                75));

        subjects.add(new Subject("coa", "Computer Organization & Architecture", "Core CS",
                "#FFEAA7", "⚡",
                Arrays.asList("Number Systems", "Boolean Algebra", "Logic Gates",
                        "Flip Flops & Registers", "ALU Design", "Memory Hierarchy",
                        "Cache Memory", "Pipelining", "Instruction Set Architecture",
                        "Interrupts", "DMA", "RISC vs CISC"),
                "Tested in hardware and embedded system interviews.",
                70));

        // Programming
        subjects.add(new Subject("java", "Java Programming", "Programming",
                "#FF8C42", "☕",
                Arrays.asList("OOP Concepts", "Classes & Objects", "Inheritance",
                        "Polymorphism", "Abstraction & Interfaces", "Exception Handling",
                        "Collections Framework", "Generics", "Streams & Lambda",
                        "Multithreading", "Design Patterns", "Java 8+ Features"),
                "Most popular language for placements. Must know deeply.",
                90));

        subjects.add(new Subject("cpp", "C++ Programming", "Programming",
                "#6C5CE7", "⚡",
                Arrays.asList("Pointers & References", "Memory Management",
                        "STL (Vector, Map, Set)", "Templates", "OOP in C++",
                        "File Handling", "Operator Overloading", "Smart Pointers",
                        "Move Semantics", "Lambda in C++", "Competitive Programming"),
                "Essential for competitive programming and system software.",
                85));

        subjects.add(new Subject("python", "Python Programming", "Programming",
                "#A8E6CF", "🐍",
                Arrays.asList("Python Basics", "Lists, Tuples, Dicts", "List Comprehensions",
                        "Functions & Decorators", "OOP in Python", "File I/O",
                        "Regular Expressions", "NumPy Basics", "Pandas Basics",
                        "Flask/FastAPI Basics", "Async Python", "Testing"),
                "Great for scripting, data science, and backend development.",
                80));

        subjects.add(new Subject("oop", "Object Oriented Programming", "Programming",
                "#FFD93D", "🎯",
                Arrays.asList("Classes & Objects", "Encapsulation", "Inheritance",
                        "Polymorphism", "Abstraction", "SOLID Principles",
                        "Design Patterns - Creational", "Design Patterns - Structural",
                        "Design Patterns - Behavioral", "UML Diagrams"),
                "Conceptual clarity on OOP is tested in every interview.",
                88));

        // Development
        subjects.add(new Subject("webdev", "Web Development", "Development",
                "#FD79A8", "🌐",
                Arrays.asList("HTML5 Fundamentals", "CSS3 & Flexbox", "JavaScript Basics",
                        "DOM Manipulation", "ES6+ Features", "Fetch API & Ajax",
                        "Responsive Design", "Git & GitHub", "REST APIs",
                        "JSON & XML", "Browser DevTools", "Web Security Basics"),
                "Needed for frontend and full-stack development roles.",
                75));

        subjects.add(new Subject("react", "React.js", "Development",
                "#74B9FF", "⚛️",
                Arrays.asList("Components & JSX", "Props & State", "Hooks (useState, useEffect)",
                        "Context API", "React Router", "Redux Toolkit",
                        "Component Lifecycle", "Performance Optimization",
                        "Custom Hooks", "Testing with Jest", "Next.js Basics"),
                "Most in-demand frontend framework for 2024 placements.",
                78));

        subjects.add(new Subject("nodejs", "Node.js & Backend", "Development",
                "#00B894", "🟢",
                Arrays.asList("Node.js Fundamentals", "Express.js", "REST API Design",
                        "Middleware", "Authentication & JWT", "MongoDB & Mongoose",
                        "Database Integration", "File System Module",
                        "Streams & Buffers", "WebSockets", "Microservices Basics",
                        "Docker Basics"),
                "Key for full-stack and backend developer roles.",
                72));

        // Modern Tech
        subjects.add(new Subject("cloud", "Cloud Computing", "Modern Tech",
                "#81ECEC", "☁️",
                Arrays.asList("Cloud Fundamentals", "AWS Core Services", "Azure Basics",
                        "GCP Overview", "IaaS, PaaS, SaaS", "Serverless Computing",
                        "Containers & Docker", "Kubernetes Basics",
                        "Cloud Storage", "CI/CD Pipelines", "Cloud Security",
                        "Cost Optimization"),
                "High demand for cloud engineers. AWS certification is a bonus.",
                70));

        subjects.add(new Subject("ml", "Machine Learning", "Modern Tech",
                "#A29BFE", "🤖",
                Arrays.asList("ML Fundamentals", "Linear Regression", "Logistic Regression",
                        "Decision Trees", "Random Forest", "SVM", "K-Means Clustering",
                        "Neural Networks", "Overfitting & Regularization",
                        "Model Evaluation Metrics", "Feature Engineering",
                        "scikit-learn", "Pandas & NumPy"),
                "Required for data science and AI/ML roles.",
                68));

        subjects.add(new Subject("ai", "Artificial Intelligence", "Modern Tech",
                "#FAB1A0", "🧠",
                Arrays.asList("AI Fundamentals", "Search Algorithms", "BFS/DFS in AI",
                        "A* Algorithm", "Heuristic Functions", "Knowledge Representation",
                        "Expert Systems", "Natural Language Processing",
                        "Computer Vision Basics", "Reinforcement Learning",
                        "Ethical AI", "Transformer Architecture"),
                "Growing field with many opportunities in top tech companies.",
                65));

        return subjects;
    }

    public static String getSubjectCategory(String subjectId) {
        for (Subject s : getAllSubjects()) {
            if (s.getId().equals(subjectId)) return s.getCategory();
        }
        return "General";
    }
}
