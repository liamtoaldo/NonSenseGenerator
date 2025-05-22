#### User story 1.1
Given a user is on the sentence input part in the UI
And has typed a valid (not empty) sentence
When the user submits the sentence for analysis
Then the system analyzes the structure using Google's `analyzeSyntax` and displays the syntactic tree
#### User story 1.2
Given a user is on the sentence input part in the UI
And submits an empty or invalid sentence
When the user submits the sentence for analysis
Then the system shows an error message to the user
#### User story 2
Given a user has input a sentence and the program has extracted terms from it
And the user has selected either a predefined template or a random one
When the user asks for a sentence generation
Then the system creates and displays a grammatically valid nonsense sentence using random terms and terms from the input
#### User story 3
Given a nonsense sentence has been generated
When the sentence is analyzed for toxicity using Google's `moderateText`
Then the toxicity result for the sentence is displayed to the user
#### User story 4.1
Given a sentence has been input and terms have been extracted
And the user choose to save the terms for future use
When the user confirms it through analisys
Then the selected terms are saved in the public dictionary for future use and the user is notified
#### User story 4.2
Given a term from the input is already present in the dictionary
When the user attempts to add it again
Then the system ignores the duplicate
#### User story 5.1
Given a user has selected the tense (past, present, future)
When the user generates the sentence
Then the system applies the correct tense consistently in the generated sentence
#### User story 5.2
Given the user does not select any tense
When the user generates the sentence
Then the system uses a random tense
#### User story 6.1
Given a nonsense sentence has been generated
When the user decides to save the generated sentence
Then the sentence is stored with metadata (time, toxicity level, tense)
#### User story 6.2
Given users have previously saved sentences
When they access the public history log
Then a chronological list of saved sentences is displayed with relevant details