# User Stories
For more information, please consult the [project on Jira](https://i4fb.atlassian.net/jira/software/projects/CCS/boards/1).
## US1
1. As a user I want to input a sentence so that I can validate the sentence structure and see the syntactic tree.
### Description
- Display input form where the user can input a sentence and a button to analyze it
- Analyze the given sentence and produce a syntactic tree
- Display the syntactic tree to the user
- Display an error message if service is not responding
## US 2
2. As a user I want the application to extract terms from my input and use them to generate a nonsense sentence, either randomly or by selecting a predefined template, so that I can reuse it in other contexts.
### Description
- Display input form where the user can input a sentence
- Extract terms and save them upon request
- Show a menu where the user can select a template or generate one randomly
- Use the template and all the terms saved in the application (which can include the ones inputted by the user) to generate a nonsense sentence with correct grammar
- Show the generated sentence to the user
## US 3
3. As a user I want the application to show the toxicity level of the generated sentence so that I can decide whether it is appropriate to share.
### Description
- Display a button to analyze the sentence toxicity after a sentence is generated and shown to the user
- Analyze the sentence toxicity upon confirmation
- Show the toxicity analysis result to the user
- Display an error message if service is not responding
## US 4
4. As a user I want to add terms from my input sentence to the available terms so that they will be re-used in the future.
### Description
- Display a menu to the user to select whether or not his input terms will be saved
- Upon confirmation, validate the selection
- Save the terms in the application
## US 5
5. As a user I want to select a time for the sentence to be produced so that I can generate phrases that fit specific temporal contexts.
### Description
- Display a menu where the user can select a specific tense for the generated sentence
- Use his choice for the generation process
- Use random tenses if the user didn’t select one
## US 6
6. As a user I want to save the sentences that I created so that I can review or reuse them later from a public history log.
### Description
- Keep a public history of generated sentences
- Update this history whenever any user generates a sentence
- Show the history to the user