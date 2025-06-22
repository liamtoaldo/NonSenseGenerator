/*
Utilizzo di localStorage per salvare le frasi inserite e le frasi generate.
Ad ogni generazione di una frase, viene aggiunta alla lista delle frasi salvate la coppia input-output.
*/

const HISTORY_NAME = 'Sentences';

const getHistory = () => {
    const history = window.localStorage.getItem(HISTORY_NAME);
    return history ? JSON.parse(history) : [];
}

const addSentenceToHistory = (input, output) => {
    const history = getHistory();
    history.unshift({ input: input, output: output, template: selectedTemplate, tense: selectedTense });
    localStorage.setItem(HISTORY_NAME, JSON.stringify(history));
    updateHistory();
}

const restoreFromHistory = (input, output, template, tense) => {
    if (!template) {
        template = "Random Template";
    }
    $('#inputText').val(input);
    $('#outputText').text(output);
    selectTemplate(template);
    selectTense(tense);
    clearToxicityAnalysisCanvas();
    clearSyntaxTreeCanvas();
    showAlert("Sentences set from history", "info");
}

const updateHistory = () => {
    const container = $('#historyContainer');
    const history = getHistory();
    container.empty();
    if (history.length === 0) {
        container.append('<p class="text-secondary">No sentences in history.</p>');
        return;
    }
    let html = '<ul class="list-group">';
    history.forEach((item, index) => {
        html += `<li class="list-group-item">
            <div class="d-flex flex-column justify-content-between">
                <small><strong>Input:</strong> ${item.input}</small>
                <hr class="mb-1 mt-1" style="border: 1 solid black;" />
                <small class="text-secondary"><strong>Output:</strong> ${item.output}</small>
            </div>
            <button class="btn btn-success btn-sm float-end" onclick="restoreFromHistory('${item.input}', '${item.output}', '${item.template}', '${item.tense}')"><i class="bi bi-arrow-clockwise"></i></button>
        </li>`;
    });
    html += '</ul>';
    container.append(html);
}

const initializeHistory = () => {
    if(!window.localStorage.getItem(HISTORY_NAME)) {
        window.localStorage.setItem(HISTORY_NAME, JSON.stringify([]));
    }
    updateHistory();
}

/** Global NonSense Generations */

const CLIPBOARD_CHECK = '<i class="bi bi-clipboard-check"></i>';

const updateGlobalHistory = (amount) => {
    const container = $('#globalHistoryContainer');
    container.empty();
    $.ajax({
        url: '/api/v1/nonsense/history/generated/' + amount,
        type: 'GET',
        dataType: 'json',
        success: (data) => {
            let history = data;
            if (history.length === 0) {
                container.append('<p class="text-warning">No global sentences available.</p>');
                return;
            }
            let html = '<ul class="list-group">';
            history.forEach((item, index) => {
                html += `<li class="list-group-item">
                    <div class="d-flex justify-content-between">
                        <div class="d-flex flex-column justify-content-between">
                            <strong>${item.text}</strong>
                            <small class="text-secondary">${item.date}</small>
                        </div>
                        <div class="d-flex align-items-center">
                            <button class="btn" onclick="copyToClipboard('${item.text}'); this.innerHTML = CLIPBOARD_CHECK;" type="button" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Copy to clipboard"><i class="bi bi-clipboard"></i></button>
                        </div>
                    </div>
                </li>`;
            });
            html += '</ul>';
            container.append(html);
            initializeTooltips();
        },
        error: (error) => {
            console.error('Error fetching global history:', error);
        }
    });
}