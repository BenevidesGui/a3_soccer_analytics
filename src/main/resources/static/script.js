console.log("🔥 PROVA 1: script.js FOI CARREGADO");

document.addEventListener("DOMContentLoaded", function () {

    const goals = window.goalsData || {};
    const avgGoals = window.averageGoals || {};

    const labels = Object.keys(goals);
    const goalsValues = Object.values(goals);
    const avgValues = Object.values(avgGoals);

    const goalsCanvas = document.getElementById('goalsChart');
    const avgCanvas = document.getElementById('avgGoalsChart');

    console.log("GOALS:", window.goalsData);
    console.log("AVG:", window.averageGoals);

    if (!goalsCanvas || !avgCanvas) {
        console.error("❌ Canvas não encontrado");
        return;
    }

    // =========================
    // 1. GOLS POR LIGA
    // =========================
    new Chart(goalsCanvas, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Gols por Liga',
                data: goalsValues,
                backgroundColor: 'rgba(255, 107, 53, 0.6)',
                borderColor: 'rgba(255, 107, 53, 1)',
                borderWidth: 1,
                borderRadius: 5
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: { labels: { color: '#fff' } }
            },
            scales: {
                x: { ticks: { color: '#fff' } },
                y: { ticks: { color: '#fff' }, beginAtZero: true }
            }
        }
    });

    // =========================
    // 2. MÉDIA DE GOLS POR JOGO
    // =========================
    new Chart(avgCanvas, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Média de Gols por Partida',
                data: avgValues,
                backgroundColor: 'rgba(0, 200, 83, 0.6)',
                borderColor: 'rgba(0, 200, 83, 1)',
                borderWidth: 1,
                borderRadius: 5
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: { labels: { color: '#fff' } }
            },
            scales: {
                x: { ticks: { color: '#fff' } },
                y: {
                    ticks: { color: '#fff' },
                    beginAtZero: true
                }
            }
        }
    });

});