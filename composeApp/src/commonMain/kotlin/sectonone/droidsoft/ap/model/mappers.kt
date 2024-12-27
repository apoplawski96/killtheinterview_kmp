package sectonone.droidsoft.ap.model

import sectonone.droidsoft.ap.db.InterviewSummary

val InterviewSummary.toUiModel
    get() = InterviewHistorySummaryUI(
        id = id.toInt(),
        answeredCount = answeredCount.toInt(),
        failedCount = failedCount.toInt(),
        categoriesSummary = categoriesSummary.split(", "),
        interviewDate = interviewDate
    )