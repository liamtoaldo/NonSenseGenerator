//Risolve il problema di importazione di Chart.js e ChartDataLabels
if (window.ChartDataLabels) {
    Chart.register(ChartDataLabels);
} else {
    console.error("ChartDataLabels plugin not found. Make sure it's loaded before index.js");
}

// Template and Tense handling

const selectedTemplatePg = $('#selectedTemplate');
const selectedTensePg = $('#selectedTense');
let selectedTemplate = "";
let selectedTense = "PRESENT";

const selectTemplate = (template) => {
    selectedTemplate = template;
    selectedTemplatePg.removeClass('d-none').html('<span class="text-secondary"><i class="bi bi-layout-text-sidebar-reverse"></i> Selected template: </span>' + template);
}

const selectTense = (tense) => {
    selectedTense = tense;
    selectedTensePg.removeClass('d-none').html('<span class="text-secondary"><i class="bi bi-clock"></i> Selected tense: </span>' + tense);
    console.log(`Selected tense: ${tense}`);
}

const initializeTemplates = () => {
    $.ajax({
        url: '/api/v1/nonsense/dictionary/templates',
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            templates = data;
            const templatesDropdown = $('#templatesDropdown');
            if (templatesDropdown) {
                templates.forEach(t => {
                    templatesDropdown.append(`<button class="dropdown-item">${t.template}</button>`);
                });
                templatesDropdown.on('click', '.dropdown-item', function (e) {
                    e.preventDefault();
                    const template = $(this).html();
                    selectTemplate(template);
                });
            }
            //selectedTemplatePg.removeClass('d-none').html('<span class="text-secondary">Selected template: </span>' + "Random Template");
            selectTemplate("Random Template");

            console.log("Templates loaded successfully:", data);
        },
        error: function (xhr, status, error) {
            console.error("Error loading templates:", status, error);
        }
    });
}

const initializeTenses = () => {
    tenses = ["PAST", "PRESENT", "FUTURE"];
    const tensesDropdown = $('#tensesDropdown');
    if (tensesDropdown) {
        tensesDropdown.empty();
        tenses.forEach(t => {
            tensesDropdown.append(`<button class="dropdown-item">${t}</button>`);
        });
        tensesDropdown.on('click', '.dropdown-item', function (e) {
            e.preventDefault();
            const tense = $(this).html();
            selectTense(tense);
        });
        //selectedTensePg.removeClass('d-none').html('<span class="text-secondary">Selected tense: </span>' + "Present");
        selectTense("PRESENT");
    }
    console.log("Tenses loaded successfully:", tenses);
}

// Miscellaneous functions

const initializeTooltips = () => {
    $('[data-bs-toggle="tooltip"]').tooltip();
    $('[data-bs-toggle="tooltip"]').on('click', function () {
        $(this).blur();
    });
}

const showAlert = (message, type = 'success') => {
    const alert = `<div class="alert alert-${type} alert-dismissible fade show position-fixed bottom-0 end-0 m-3" role="alert">
        <strong>${type.charAt(0).toUpperCase() + type.slice(1)}!</strong> ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>`;
    $('body').append(alert);
    setTimeout(() => {
        $('.alert').alert('close');
    }, 5000);
}

const copyToClipboard = (text) => {
    navigator.clipboard.writeText(text).then(() => {
        showAlert("Text copied to clipboard!", "success");
    }).catch(err => {
        console.error('Failed to copy text: ', err);
        showAlert("Failed to copy text to clipboard.", "danger");
    });
}

// Main functions

$(document).ready(function () {

    initializeTemplates();
    initializeTenses();
    initializeHistory();
    initializeTooltips();

    // Sidebar toggle functionality
    const sidebarToggle = $('#sidebarToggle');
    const sidebar = $('#sidebar');
    if (sidebarToggle && sidebar) {
        sidebarToggle.on('click', function () {
            sidebar.toggleClass('collapsed');
        });
    }

    // Buttons variables
    const analyzeSyntaxButton = $('#analyzeSyntaxButton');
    const analyzeToxicityButton = $('#analyzeToxicityButton')
    const generateButton = $('#generateButton');
    const globalHistoryButton = $('#globalHistoryButton');
    const globalHistoryTitle = $('#globalHistoryTitle');
    const inputText = $('#inputText');
    // Action buttons
    if (inputText && analyzeSyntaxButton && analyzeToxicityButton && generateButton) {
        analyzeSyntaxButton.on('click', function () {
            analyzesyntax(inputText);
        });
        analyzeToxicityButton.on('click', function () {
            const sentence = $('#outputText').val();
            analyzeToxicity(sentence);
        });
        generateButton.on('click', function () {
            console.log(inputText.val());
            console.log(selectedTemplate);
            console.log(selectedTense);
            if (selectedTemplate === "Random Template") selectedTemplate = "";
            console.log(`Selected template: ${selectedTemplate}`);
            generateSentence(inputText.val(), selectedTemplate, selectedTense);
        });
        const GLOBAL_HISTORY_AMOUNT = 10;
        globalHistoryButton.on('click', function () {
            updateGlobalHistory(GLOBAL_HISTORY_AMOUNT);
        });
        globalHistoryTitle.html('Last Global ' + GLOBAL_HISTORY_AMOUNT + ' NonSense Generations');
    }

});