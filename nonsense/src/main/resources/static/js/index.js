//Risolve il problema di importazione di Chart.js e ChartDataLabels
if (window.ChartDataLabels) {
    Chart.register(ChartDataLabels);
} else {
    console.error("ChartDataLabels plugin not found. Make sure it's loaded before index.js");
}

// Template and Tense handling

const selectedTemplatePg = $('#selectedTemplate');
const selectedTensePg = $('#selectedTense');
let selectedTemplate = null;
let selectedTense = null;

const selectTemplate = (template) => {
    selectedTemplate = template;
    selectedTemplatePg.removeClass('d-none').html('<span class="text-secondary">Selected template: </span>' + template);
    console.log(`Selected template: ${template}`);
}

const selectTense = (tense) => {
    selectedTense = tense;
    selectedTensePg.removeClass('d-none').html('<span class="text-secondary">Selected tense: </span>' + tense);
    console.log(`Selected tense: ${tense}`);
}

const initializeTemplates = () => {
    $.ajax({
        url: '/api/v1/nonsense/dictionary/templates',
        method: 'GET',
        dataType: 'json',
        success: function(data) {
            templates = data;
            const templatesDropdown = $('#templatesDropdown');
            if (templatesDropdown) {
                templatesDropdown.empty();
                templates.forEach(t => {
                    templatesDropdown.append(`<button class="dropdown-item">${t.template}</button>`);
                });
                templatesDropdown.on('click', '.dropdown-item', function (e) {
                    e.preventDefault();
                    const template = $(this).html();
                    selectTemplate(template);
                });
            }
            console.log("Templates loaded successfully:", data);
        },
        error: function(xhr, status, error) {
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
    }
    console.log("Tenses loaded successfully:", tenses);
}

// Miscellaneous functions

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

// Main functions

$(document).ready(function () {

    initializeTemplates();
    initializeTenses();

    // Tooltips initialization
    $('[data-bs-toggle="tooltip"]').tooltip();
    $('[data-bs-toggle="tooltip"]').on('click', function () {
        $(this).blur();
    });

    // Sidebar toggle functionality
    const sidebarToggle = $('#sidebarToggle');
    const sidebar = $('#sidebar');
    if (sidebarToggle && sidebar) {
        sidebarToggle.on('click', function() {
            sidebar.toggleClass('collapsed');
        });
    }

    // Buttons variables
    const analyzeSyntaxButton = $('#analyzeSyntaxButton');
    const generateButton = $('#generateButton');
    const inputText = $('#inputText');
    // Action buttons
    if (analyzeSyntaxButton && inputText) {
        analyzeSyntaxButton.on('click', () => analyzesyntax(inputText));
        generateButton.on('click', () => generateSentence(inputText.val(), selectedTemplate, selectedTense));
    }

});