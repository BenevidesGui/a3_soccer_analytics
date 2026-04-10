console.log("🔥 PROVA 1: script.js FOI CARREGADO");

document.addEventListener("DOMContentLoaded", function () {

    const goals = window.goalsData || {};

    const labels = Object.keys(goals);
    const values = Object.values(goals);

    const canvas = document.getElementById('goalsChart');

    if (!canvas) {
        console.error("❌ Canvas 'goalsChart' não encontrado");
        return;
    }

    new Chart(canvas, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Gols por Liga',
                data: values,
                backgroundColor: 'rgba(255, 107, 53, 0.6)',
                borderColor: 'rgba(255, 107, 53, 1)',
                borderWidth: 1,
                borderRadius: 5,
                borderSkipped: false,
            }]
        },
        options: {
            backgroundColor: '#2c2c2c',
            responsive: true,
            plugins: {
                legend: {
                    display: true,
                    labels: {
                        color: '#fff',
                        font: {
                            size: 14
                        }
                    }
                },
                tooltip: {
                    backgroundColor: 'rgba(0,0,0,0.8)',
                    titleColor: '#fff',
                    bodyColor: '#fff'
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    grid: {
                        display: false
                    },
                    ticks: {
                        color: '#fff',
                        stepSize: 200,
                        max: 2000
                    }
                },
                x: {
                    grid: {
                        display: false
                    },
                    ticks: {
                        color: '#fff'
                    }
                }
            }
        }
    });

});