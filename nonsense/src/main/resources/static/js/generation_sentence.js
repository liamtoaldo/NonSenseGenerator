const generateSentence = (text, template, tense) => {
    if (!text || text.trim() === '') {
        showAlert("Input cannot be empty.", "danger");
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
            console.error('Error:', error, 'Status:', status, 'Response:', xhr.responseText);
            let errorMessage = "An unexpected error occurred.";

            if (status === 'timeout') {
                errorMessage = "API call failed or timed out.";
            } else if (xhr.status === 400) {
                if (xhr.responseText && xhr.responseText.includes("convert")) {
                    errorMessage = "The selected tense is not supported.";
                } else {
                    errorMessage = "Invalid request. Please check your input.";
                }
            } else if (xhr.status === 500) {
                if (xhr.responseText && xhr.responseText.includes("No templates available")) {
                    errorMessage = "Template not found for random generation.";
                } else {
                    errorMessage = "API call failed due to a server error.";
                }
            } else if (status === 'error') {
                errorMessage = "API call failed. Check network connection.";
            }

            showAlert(errorMessage, "danger");
        }
    });
};
