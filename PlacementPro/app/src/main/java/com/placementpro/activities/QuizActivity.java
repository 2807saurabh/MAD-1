package com.placementpro.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.placementpro.R;
import com.placementpro.database.DatabaseHelper;
import com.placementpro.utils.SessionManager;
import java.util.*;

public class QuizActivity extends AppCompatActivity {

    private TextView tvQuestion, tvTimer, tvScore, tvQuestionNo;
    private Button btnA, btnB, btnC, btnD, btnNext;
    private ProgressBar pbTimer;

    private SessionManager session;
    private DatabaseHelper db;

    private List<String[]> questions; // [question, A, B, C, D, correct]
    private int currentQ = 0, score = 0;
    private CountDownTimer timer;
    private boolean answered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        session = new SessionManager(this);
        db = DatabaseHelper.getInstance(this);

        Toolbar toolbar = findViewById(R.id.toolbar_quiz);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Quick Quiz");
        }

        tvQuestion = findViewById(R.id.tv_question);
        tvTimer = findViewById(R.id.tv_timer);
        tvScore = findViewById(R.id.tv_quiz_score);
        tvQuestionNo = findViewById(R.id.tv_question_no);
        btnA = findViewById(R.id.btn_option_a);
        btnB = findViewById(R.id.btn_option_b);
        btnC = findViewById(R.id.btn_option_c);
        btnD = findViewById(R.id.btn_option_d);
        btnNext = findViewById(R.id.btn_next_question);
        pbTimer = findViewById(R.id.pb_timer);

        questions = getQuizQuestions();
        Collections.shuffle(questions);
        questions = questions.subList(0, Math.min(10, questions.size()));

        showQuestion();

        btnNext.setOnClickListener(v -> {
            currentQ++;
            if (currentQ < questions.size()) showQuestion();
            else showResult();
        });

        View.OnClickListener optionClick = v -> {
            if (answered) return;
            answered = true;
            if (timer != null) timer.cancel();

            String[] q = questions.get(currentQ);
            String correct = q[5];
            Button clicked = (Button) v;

            if (clicked.getText().toString().startsWith(correct)) {
                clicked.setBackgroundColor(Color.parseColor("#00C853"));
                score++;
                tvScore.setText("Score: " + score);
                session.addXP(15);
            } else {
                clicked.setBackgroundColor(Color.parseColor("#FF1744"));
                // Show correct
                if (correct.equals("A")) btnA.setBackgroundColor(Color.parseColor("#00C853"));
                else if (correct.equals("B")) btnB.setBackgroundColor(Color.parseColor("#00C853"));
                else if (correct.equals("C")) btnC.setBackgroundColor(Color.parseColor("#00C853"));
                else btnD.setBackgroundColor(Color.parseColor("#00C853"));
            }
            btnNext.setVisibility(View.VISIBLE);
        };

        btnA.setOnClickListener(optionClick);
        btnB.setOnClickListener(optionClick);
        btnC.setOnClickListener(optionClick);
        btnD.setOnClickListener(optionClick);
    }

    private void showQuestion() {
        answered = false;
        btnNext.setVisibility(View.GONE);

        int defaultColor = getResources().getColor(R.color.card_bg);
        btnA.setBackgroundColor(defaultColor);
        btnB.setBackgroundColor(defaultColor);
        btnC.setBackgroundColor(defaultColor);
        btnD.setBackgroundColor(defaultColor);

        String[] q = questions.get(currentQ);
        tvQuestionNo.setText("Q" + (currentQ + 1) + "/" + questions.size());
        tvQuestion.setText(q[0]);
        btnA.setText("A. " + q[1]);
        btnB.setText("B. " + q[2]);
        btnC.setText("C. " + q[3]);
        btnD.setText("D. " + q[4]);

        if (timer != null) timer.cancel();
        timer = new CountDownTimer(20000, 1000) {
            public void onTick(long ms) {
                int sec = (int) (ms / 1000);
                tvTimer.setText(sec + "s");
                pbTimer.setProgress(sec * 5);
            }
            public void onFinish() {
                tvTimer.setText("0s");
                if (!answered) {
                    answered = true;
                    btnNext.setVisibility(View.VISIBLE);
                    Toast.makeText(QuizActivity.this, "Time's up!", Toast.LENGTH_SHORT).show();
                }
            }
        }.start();
    }

    private void showResult() {
        if (timer != null) timer.cancel();
        db.saveQuizScore(session.getUserId(), "Mixed", score, questions.size());
        int xp = score * 15;
        session.addXP(xp);

        String grade = score >= 8 ? "🏆 Excellent!" : score >= 6 ? "👍 Good!" : score >= 4 ? "📚 Keep Practicing" : "💪 Don't Give Up!";
        new AlertDialog.Builder(this)
                .setTitle("Quiz Complete! " + grade)
                .setMessage("Score: " + score + "/" + questions.size() +
                        "\nXP Earned: +" + xp +
                        "\nPercentage: " + (score * 100 / questions.size()) + "%")
                .setPositiveButton("Retry", (d, w) -> {
                    currentQ = 0; score = 0;
                    Collections.shuffle(questions);
                    tvScore.setText("Score: 0");
                    showQuestion();
                })
                .setNegativeButton("Done", (d, w) -> finish())
                .setCancelable(false).show();
    }

    private List<String[]> getQuizQuestions() {
        List<String[]> q = new ArrayList<>();
        // format: question, A, B, C, D, correct_letter
        q.add(new String[]{"Which data structure uses LIFO order?", "Queue", "Stack", "Heap", "Tree", "B"});
        q.add(new String[]{"Time complexity of Binary Search?", "O(n)", "O(n²)", "O(log n)", "O(1)", "C"});
        q.add(new String[]{"Which SQL command removes rows from a table?", "DROP", "TRUNCATE", "DELETE", "REMOVE", "C"});
        q.add(new String[]{"What does OOP stand for?", "Object Oriented Protocol", "Object Oriented Programming", "Open Object Protocol", "Object Operator Programming", "B"});
        q.add(new String[]{"Which layer handles IP addressing in OSI model?", "Transport", "Network", "Data Link", "Session", "B"});
        q.add(new String[]{"Deadlock prevention strategy?", "Circular Wait", "Mutual Exclusion", "Resource Allocation", "Banker's Algorithm", "D"});
        q.add(new String[]{"What is polymorphism in OOP?", "Multiple classes", "One interface multiple forms", "Data hiding", "Code reuse", "B"});
        q.add(new String[]{"Which sorting algorithm has best worst-case?", "Bubble Sort", "Quick Sort", "Merge Sort", "Selection Sort", "C"});
        q.add(new String[]{"TCP vs UDP — which is reliable?", "UDP", "TCP", "Both", "Neither", "B"});
        q.add(new String[]{"Primary key constraint in SQL?", "Allows duplicates", "Can be NULL", "Unique and NOT NULL", "Only unique", "C"});
        q.add(new String[]{"Normalization removes?", "Records", "Data redundancy", "Primary keys", "Foreign keys", "B"});
        q.add(new String[]{"Which Java keyword prevents inheritance?", "static", "private", "final", "abstract", "C"});
        q.add(new String[]{"BFS uses which data structure?", "Stack", "Queue", "Tree", "Graph", "B"});
        q.add(new String[]{"Virtual memory extends?", "Cache", "ROM", "RAM using disk", "CPU registers", "C"});
        q.add(new String[]{"REST stands for?", "Real Entity State Transfer", "Representational State Transfer", "Remote Entity Service Transfer", "Reliable Entity State Transfer", "B"});
        q.add(new String[]{"In Python, which is mutable?", "Tuple", "String", "List", "int", "C"});
        q.add(new String[]{"What is a thread?", "Process", "Lightweight process", "Memory block", "CPU core", "B"});
        q.add(new String[]{"Hash collision means?", "Same hash for different keys", "Different hash for same key", "Hash overflow", "Empty hash table", "A"});
        q.add(new String[]{"Which is NOT a cloud service model?", "IaaS", "PaaS", "SaaS", "OaaS", "D"});
        q.add(new String[]{"Big O of accessing array element?", "O(n)", "O(log n)", "O(1)", "O(n²)", "C"});
        return q;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) timer.cancel();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) { onBackPressed(); return true; }
        return super.onOptionsItemSelected(item);
    }
}
