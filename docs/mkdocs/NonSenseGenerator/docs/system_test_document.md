# System Test Document
## Acceptance Criterias
The following acceptance criteria are derived from the user stories and define the expected behavior of the system:
### User story 1.1
Given a user is on the sentence input part in the UI<br>
And has typed a valid (not empty) sentence<br>
When the user submits the sentence for analysis<br>
Then the system analyzes the structure using Google's `analyzeSyntax` and displays the syntactic tree<br>
### User story 1.2
Given a user is on the sentence input part in the UI<br>
And submits an empty or invalid sentence<br>
When the user submits the sentence for analysis<br>
Then the system shows an error message to the user<br>
### User story 2
Given a user has input a sentence and the program has extracted terms from it<br>
And the user has selected either a predefined template or a random one<br>
When the user asks for a sentence generation<br>
Then the system creates and displays a grammatically valid nonsense sentence using random terms and terms from the input<br>
### User story 3
Given a nonsense sentence has been generated<br>
When the sentence is analyzed for toxicity using Google's `moderateText`<br>
Then the toxicity result for the sentence is displayed to the user<br>
### User story 4.1
Given a sentence has been input and terms have been extracted<br>
And the user choose to save the terms for future use<br>
When the user confirms it through analisys<br>
Then the selected terms are saved in the public dictionary for future use and the user is notified<br>
### User story 4.2
Given a term from the input is already present in the dictionary<br>
When the user attempts to add it again<br>
Then the system ignores the duplicate<br>
### User story 5.1
Given a user has selected the tense (past, present, future)<br>
When the user generates the sentence<br>
Then the system applies the correct tense consistently in the generated sentence<br>
### User story 5.2
Given the user does not select any tense<br>
When the user generates the sentence<br>
Then the system uses the present tense<br>
### User story 6.1
Given a nonsense sentence has been generated<br>
When the user decides to save the generated sentence<br>
Then the sentence is stored with metadata (time, toxicity level, tense)<br>
### User story 6.2
Given users have previously saved sentences<br>
When they access the public history log<br>
Then a chronological list of saved sentences is displayed with relevant details
## System Test Report

| Acceptance Criteria | Date | Status | Notes |
|---------------------|------|--------|-------|
| User story 1.1 | 22/06/2025 | Pass |  |
| User story 1.2 | 22/06/2025 | Pass |  |
| User story 2 | 22/06/2025 | Pass |  |
| User story 3 | 22/06/2025 | Pass |  |
<!-- TODO: choose or not? -->
<!--
| User story 4.1 | 22/06/2025 | Pass |  |
| User story 4.2 | 22/06/2025 | Pass |  |
-->
| User story 5.1 | 22/06/2025 | Pass |  |
| User story 5.2 | 22/06/2025 | Pass |  |
<!-- TODO: choose or not? -->
<!--
| User story 6.1 | 22/06/2025 | Pass |  |
-->
| User story 6.2 | 22/06/2025 | Pass |  |