package com.athensease;

public class PlannerNew {
    
    public static void main (String args[]) {
        QuestionnaireNew questionnaire = new QuestionnaireNew();
        questionnaire.gatherHolidayDuration();
        questionnaire.gatherTrailheads();
        questionnaire.gatherBudget();
        questionnaire.chooseCategories();
    }
}
