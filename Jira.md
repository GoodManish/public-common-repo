# JIRA Story – Datalake Export CronJob Date Handling

## Title
Make Datalake Export CronJob date-aware and resilient to missed / failed runs

---

## Background / Problem Statement

The Datalake CSV export application currently depends on a static `currentdate`
configuration value to determine the business date for data extraction and S3
folder creation.

When the Kubernetes CronJob:
- fails on the scheduled day, or
- is manually retried after midnight (UTC),

the application exports data for the **wrong date**, requiring manual updates
to Helm values and re-deployment.

This manual intervention is error-prone and operationally expensive.

---

## Objective

Make the export job **schedule-aware and self-healing**, so that:
- Business date is computed dynamically at runtime
- Failed or delayed runs still export the correct previous day’s data
- No manual Helm or code changes are required for retries

---

## Functional Requirements

1. The job must dynamically compute the business date at runtime.
2. If the job runs **before the configured schedule time**, it must export data
   for the **previous day**.
3. If the job runs **at or after the schedule time**, it must export data for the
   **current day**.
4. Manual retries after midnight must still export data for the intended
   business date.
5. CSV files must continue to be uploaded to S3 under date-based folders


**Acceptance Criteria**

 Normal scheduled run exports data for the correct business date
 Failed job retried after midnight exports previous day’s data
 No manual Helm value updates required for retries
 CSV files are uploaded under correct date-based S3 folders
 Old export folders are cleaned up automatically via lifecycle policy
 Changes validated successfully in lower environment (DEV/UAT)
