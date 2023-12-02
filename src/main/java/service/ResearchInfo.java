package service;

import java.util.HashMap;
import java.util.List;

public class ResearchInfo {

    private final int research_id;
    private final String research_name;
    private final HashMap<String, String> questions;
    private final String status;

    public int getResearch_id() {
        return research_id;
    }

    public HashMap<String, String> getQuestions() {
        return questions;
    }

    public String getStatus() {
        return status;
    }

    public String getResearch_name() {
        return research_name;
    }

    public ResearchInfo(int research_id, String name, HashMap<String, String> questions, String status) {
        this.research_id = research_id;
        research_name = name;
        this.questions = questions;
        this.status = status;
    }


}
