const generateSentence = (text, template, tense) => {

    if(!text){
        showAlert("Text input is empty.", "Warning");
        return;
    }
    if(!template){
        showAlert("Template is not selected.", "Warning");
        return;
    }
    if(!tense){
        showAlert("Tense is not selected.", "Warning");
        return;
    }

    $.ajax({
        url: `/api/v1/nonsense/generate-sentence?text=${encodeURIComponent(text)}&template=${encodeURIComponent(template)}&tense=${encodeURIComponent(tense)}`,
        type: 'GET',
        success: function (data) {
            const sentence = data;
            $('#outputText').text(sentence);
            showAlert("Sentence generated", "success");
        },
        error: function (xhr, status, error) {
            showAlert("Error generating sentence", "error");
        }
    });
};
