# Welcome to the official NonSenseGenerator Documentation

## Manual

## User Stories
For more information, please consult the [project on Jira](https://i4fb.atlassian.net/jira/software/projects/CCS/boards/1).
### US1
1. As a user I want to input a sentence so that I can validate the sentence structure and see the syntactic tree.
#### Description
- Display input form where the user can input a sentence and a button to analyze it
- Analyze the given sentence and produce a syntactic tree
- Display the syntactic tree to the user
- Display an error message if service is not responding
### US 2
2. As a user I want the application to extract terms from my input and use them to generate a nonsense sentence, either randomly or by selecting a predefined template, so that I can reuse it in other contexts.
#### Description
- Display input form where the user can input a sentence
- Extract terms and save them upon request
- Show a menu where the user can select a template or generate one randomly
- Use the template and all the terms saved in the application (which can include the ones inputted by the user) to generate a nonsense sentence with correct grammar
- Show the generated sentence to the user
### US 3
3. As a user I want the application to show the toxicity level of the generated sentence so that I can decide whether it is appropriate to share.
#### Description
- Display a button to analyze the sentence toxicity after a sentence is generated and shown to the user
- Analyze the sentence toxicity upon confirmation
- Show the toxicity analysis result to the user
- Display an error message if service is not responding
### US 4
4. As a user I want to add terms from my input sentence to the available terms so that they will be re-used in the future.
#### Description
- Display a menu to the user to select whether or not his input terms will be saved
- Upon confirmation, validate the selection
- Save the terms in the application
### US 5
5. As a user I want to select a time for the sentence to be produced so that I can generate phrases that fit specific temporal contexts.
#### Description
- Display a menu where the user can select a specific tense for the generated sentence
- Use his choice for the generation process
- Use random tenses if the user didn’t select one
### US 6
6. As a user I want to save the sentences that I created so that I can review or reuse them later from a public history log.
#### Description
- Keep a public history of generated sentences
- Update this history whenever any user generates a sentence
- Show the history to the user
## Design Document
### Domain Model
### System Sequence Diagrams
### Design Class Model
### Internal Sequence Diagrams
## System Test Document
### Acceptance Criterias
The following acceptance criteria are derived from the user stories and define the expected behavior of the system:
#### User story 1.1
Given a user is on the sentence input part in the UI<br>
And has typed a valid (not empty) sentence<br>
When the user submits the sentence for analysis<br>
Then the system analyzes the structure using Google's `analyzeSyntax` and displays the syntactic tree<br>
#### User story 1.2
Given a user is on the sentence input part in the UI<br>
And submits an empty or invalid sentence<br>
When the user submits the sentence for analysis<br>
Then the system shows an error message to the user<br>
#### User story 2
Given a user has input a sentence and the program has extracted terms from it<br>
And the user has selected either a predefined template or a random one<br>
When the user asks for a sentence generation<br>
Then the system creates and displays a grammatically valid nonsense sentence using random terms and terms from the input<br>
#### User story 3
Given a nonsense sentence has been generated<br>
When the sentence is analyzed for toxicity using Google's `moderateText`<br>
Then the toxicity result for the sentence is displayed to the user<br>
#### User story 4.1
Given a sentence has been input and terms have been extracted<br>
And the user choose to save the terms for future use<br>
When the user confirms it through analisys<br>
Then the selected terms are saved in the public dictionary for future use and the user is notified<br>
#### User story 4.2
Given a term from the input is already present in the dictionary<br>
When the user attempts to add it again<br>
Then the system ignores the duplicate<br>
#### User story 5.1
Given a user has selected the tense (past, present, future)<br>
When the user generates the sentence<br>
Then the system applies the correct tense consistently in the generated sentence<br>
#### User story 5.2
Given the user does not select any tense<br>
When the user generates the sentence<br>
Then the system uses a random tense<br>
#### User story 6.1
Given a nonsense sentence has been generated<br>
When the user decides to save the generated sentence<br>
Then the sentence is stored with metadata (time, toxicity level, tense)<br>
#### User story 6.2
Given users have previously saved sentences<br>
When they access the public history log<br>
Then a chronological list of saved sentences is displayed with relevant details

## Unit Test Report
