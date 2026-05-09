const API_BASE = 'http://localhost:8080/api/missions';

// Вкладки
document.querySelectorAll('.tab-btn').forEach(btn => {
    btn.addEventListener('click', () => {
        const tabId = btn.dataset.tab;
        document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
        document.querySelectorAll('.tab-content').forEach(c => c.classList.remove('active'));
        btn.classList.add('active');
        document.getElementById(`${tabId}-tab`).classList.add('active');
    });
});

// Загрузка файла
const uploadArea = document.getElementById('upload-area');
const fileInput = document.getElementById('file-input');

uploadArea.addEventListener('click', () => fileInput.click());
uploadArea.addEventListener('dragover', (e) => {
    e.preventDefault();
    uploadArea.style.borderColor = '#e94560';
});
uploadArea.addEventListener('dragleave', () => {
    uploadArea.style.borderColor = '#3a3a6a';
});
uploadArea.addEventListener('drop', (e) => {
    e.preventDefault();
    uploadArea.style.borderColor = '#3a3a6a';
    const file = e.dataTransfer.files[0];
    if (file) uploadFile(file);
});

fileInput.addEventListener('change', (e) => {
    if (e.target.files[0]) uploadFile(e.target.files[0]);
});

async function uploadFile(file) {
    const status = document.getElementById('upload-status');
    status.innerHTML = '<div class="loading"></div> Загрузка...';
    status.className = 'status';

    const formData = new FormData();
    formData.append('file', file);

    try {
        const response = await fetch(`${API_BASE}/upload`, {
            method: 'POST',
            body: formData
        });

        if (response.ok) {
            const data = await response.json();
            status.innerHTML = `✅ Миссия ${data.missionId} успешно загружена!`;
            status.className = 'status success';
        } else {
            const error = await response.text();
            status.innerHTML = `❌ Ошибка: ${error}`;
            status.className = 'status error';
        }
    } catch (error) {
        status.innerHTML = `❌ Ошибка: ${error.message}`;
        status.className = 'status error';
    }
}

// Список миссий
document.getElementById('refresh-list').addEventListener('click', loadMissions);

async function loadMissions() {
    const container = document.getElementById('missions-list');
    container.innerHTML = '<div class="loading"></div> Загрузка...';

    try {
        const response = await fetch(API_BASE);
        const missions = await response.json();

        if (missions.length === 0) {
            container.innerHTML = '<p>📭 Нет загруженных миссий</p>';
            return;
        }

        container.innerHTML = missions.map(mission => `
            <div class="mission-card" onclick="fillMissionId('${mission.missionId}')">
                <h3>${mission.missionId}</h3>
                <p>📅 ${mission.date} | 📍 ${mission.location}</p>
                <p>🏆 ${mission.outcome} | 💰 ${mission.damageCost.toLocaleString()} ¥</p>
                <p>🧙 ${mission.sorcerersCount} магов | ⚡ ${mission.techniquesCount} техник</p>
            </div>
        `).join('');
    } catch (error) {
        container.innerHTML = `<p>❌ Ошибка: ${error.message}</p>`;
    }
}

function fillMissionId(missionId) {
    document.getElementById('report-mission-id').value = missionId;
    document.querySelector('.tab-btn[data-tab="report"]').click();
}

// Генерация отчёта
document.getElementById('generate-report').addEventListener('click', generateReport);

async function generateReport() {
    const missionId = document.getElementById('report-mission-id').value.trim();
    const type = document.getElementById('report-type').value;
    const output = document.getElementById('report-output');

    if (!missionId) {
        output.innerHTML = '<p class="error">❌ Введите ID миссии</p>';
        return;
    }

    output.innerHTML = '<div class="loading"></div> Генерация отчёта...';

    try {
        const response = await fetch(`${API_BASE}/${missionId}/report`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ type: type })
        });

        if (response.ok) {
            const report = await response.text();
            output.innerHTML = `<pre>${escapeHtml(report)}</pre>`;
        } else {
            output.innerHTML = `<p class="error">❌ Миссия не найдена</p>`;
        }
    } catch (error) {
        output.innerHTML = `<p class="error">❌ Ошибка: ${error.message}</p>`;
    }
}

function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

// Загружаем список при старте
loadMissions();