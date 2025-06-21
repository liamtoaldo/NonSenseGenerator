const analyzeToxicity = (sentence) => {
    if (!sentence || sentence.trim() === '') {
        showAlert("No sentence to analyze.", "danger");
        return;
    }

    $('#toxicitySpinner').show();
    $('#toxicityChart').hide();

    $.ajax({
        url: `/api/v1/nonsense/sentence/toxicity?text=${encodeURIComponent(sentence)}`,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            $('#toxicitySpinner').hide();
            $('#toxicityChart').show();

            const categories = data.categories || [];
            const labels = categories.map(cat => cat.name);
            const values = categories.map(cat => cat.confidence);

            // eventuale distruzione del grafico precedente
            if (window.toxicityChartInstance) {
                window.toxicityChartInstance.destroy();
            }

            const ctx = document.getElementById('toxicityChart').getContext('2d');
            const maxValue = Math.max(...values.map(v => Number(v)), 0) || 1;
            
            // determinazione dei colori delle barre in base ai valori
            const barColors = values.map(v => {
                const num = Number(v);
                if (num > 0.5) return 'rgba(220, 53, 69, 0.6)';
                if (num > 0.2) return 'rgba(255, 193, 7, 0.7)';
                return 'rgba(40, 167, 69, 0.7)';
            });

            window.toxicityChartInstance = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'Toxicity Confidence',
                        data: values.map(v => Number(v).toFixed(2)), // conversione a due cifre decimali
                        backgroundColor: barColors,
                        borderColor: barColors,
                        borderWidth: 1
                    }]
                },
                options: {
                    indexAxis: 'y',
                    responsive: true,
                    plugins: {
                        legend: { display: false },
                        tooltip: {
                            callbacks: {
                                label: function(context) {
                                    return `${context.label}: ${(parseFloat(context.parsed.x).toFixed(2))}`;
                                }
                            }
                        },
                        datalabels: {
                            anchor: 'end',
                            align: 'right',
                            formatter: function(value) {
                                return parseFloat(value).toFixed(2);
                            },
                            color: '#222',
                            font: { weight: 'bold' }
                        }
                    },
                    scales: {
                        x: {
                            beginAtZero: true,
                            max: maxValue,
                            ticks: {
                                callback: function(value) {
                                    return (value * 100).toFixed(0) + '%';
                                }
                            }
                        }
                    }
                },
                plugins: [ChartDataLabels]
            });
        },
        error: function (xhr, status, error) {
            $('#toxicitySpinner').hide();
            showAlert("Failed to analyze toxicity. Please try again.", "danger");
            console.error('Toxicity analysis error:', error, xhr.responseText);
        }
    });
};