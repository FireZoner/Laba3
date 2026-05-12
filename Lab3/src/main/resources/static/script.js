const API_BASE = 'http://localhost:8080/api/missions';

// ===== ПЕРЕКЛЮЧЕНИЕ ВКЛАДОК =====
document.querySelectorAll('.tab-btn').forEach(btn => {
    btn.addEventListener('click', () => {
        const tabId = btn.dataset.tab;
        document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
        document.querySelectorAll('.tab-content').forEach(c => c.classList.remove('active'));
        btn.classList.add('active');
        document.getElementById(`${tabId}-tab`).classList.add('active');
    });
});

// ===== ЗАГРУЗКА ФАЙЛА =====
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
            status.innerHTML = ` Миссия ${data.missionId} успешно загружена!`;
            status.className = 'status success';
            loadMissions();
        } else {
            const error = await response.text();
            status.innerHTML = ` Ошибка: ${error}`;
            status.className = 'status error';
        }
    } catch (error) {
        status.innerHTML = ` Ошибка: ${error.message}`;
        status.className = 'status error';
    }
}

// ===== СПИСОК МИССИЙ =====
document.getElementById('refresh-list').addEventListener('click', loadMissions);

async function loadMissions() {
    const container = document.getElementById('missions-list');
    container.innerHTML = '<div class="loading"></div> Загрузка...';

    try {
        const response = await fetch(API_BASE);
        if (!response.ok) throw new Error('Ошибка загрузки');
        
        const missions = await response.json();

        if (missions.length === 0) {
            container.innerHTML = '<p> Нет загруженных миссий</p>';
            return;
        }

        container.innerHTML = missions.map(mission => `
            <div class="mission-card" data-id="${mission.missionId}">
                <div class="mission-info" onclick="fillMissionId('${mission.missionId}')">
                    <h3>${escapeHtml(mission.missionId)}</h3>
                    <p> ${escapeHtml(mission.date)} | ${escapeHtml(mission.location)}</p>
                    <p> ${escapeHtml(mission.outcome)} | ${mission.damageCost.toLocaleString()} ¥</p>
                    <p>? ${mission.sorcerersCount} магов | ${mission.techniquesCount} техник</p>
                </div>
                <button class="delete-btn" onclick="deleteMission('${mission.missionId}', event)">🗑️ Удалить</button>
            </div>
        `).join('');
    } catch (error) {
        container.innerHTML = `<p class="error"> Ошибка: ${error.message}</p>`;
    }
}

function fillMissionId(missionId) {
    document.getElementById('report-mission-id').value = missionId;
    document.querySelector('.tab-btn[data-tab="report"]').click();
}

// ===== УДАЛЕНИЕ МИССИИ =====
async function deleteMission(missionId, event) {
    event.stopPropagation();
    
    if (!confirm(`Удалить миссию ${missionId}? Это действие нельзя отменить.`)) {
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE}/${missionId}`, {
            method: 'DELETE'
        });
        
        if (response.ok) {
            await loadMissions();
            showToast(` Миссия ${missionId} успешно удалена`, 'success');
        } else if (response.status === 404) {
            showToast(` Миссия ${missionId} не найдена`, 'error');
        } else {
            showToast(` Ошибка при удалении миссии ${missionId}`, 'error');
        }
    } catch (error) {
        showToast(` Ошибка: ${error.message}`, 'error');
    }
}

// ===== ГЕНЕРАЦИЯ ОТЧЁТА =====
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

        if (!response.ok) {
            if (response.status === 404) {
                output.innerHTML = '<p class="error"> Миссия не найдена</p>';
            } else {
                output.innerHTML = '<p class="error"> Ошибка генерации отчёта</p>';
            }
            return;
        }

        const report = await response.text();
        output.innerHTML = `<pre>${escapeHtml(report)}</pre>`;
    } catch (error) {
        output.innerHTML = `<p class="error"> Ошибка: ${error.message}</p>`;
    }
}

// ===== ОБНОВЛЕНИЕ МИССИИ =====
document.getElementById('load-mission-btn').addEventListener('click', loadMissionForUpdate);

async function loadMissionForUpdate() {
    const missionId = document.getElementById('update-mission-id').value.trim();
    const updateForm = document.getElementById('update-form');
    const status = document.getElementById('update-status');
    
    if (!missionId) {
        status.innerHTML = '<span class="error"> Введите ID миссии</span>';
        return;
    }
    
    status.innerHTML = '<div class="loading"></div> Загрузка...';
    
    try {
        const response = await fetch(`${API_BASE}/${missionId}`);
        
        if (!response.ok) {
            throw new Error('Миссия не найдена');
        }
        
        const mission = await response.json();
        
        document.getElementById('update-location').value = mission.location || '';
        document.getElementById('update-outcome').value = mission.outcome || '';
        document.getElementById('update-damageCost').value = mission.damageCost || '';
        document.getElementById('update-comment').value = mission.comment || '';
        document.getElementById('update-add-tags').value = '';
        document.getElementById('update-remove-tags').value = '';
        
        updateForm.style.display = 'block';
        status.innerHTML = '<span class="success"> Данные загружены. Измените нужные поля и нажмите "Сохранить".</span>';
        
    } catch (error) {
        updateForm.style.display = 'none';
        status.innerHTML = `<span class="error"> ${error.message}</span>`;
    }
}

document.getElementById('update-mission-btn').addEventListener('click', updateMission);

async function updateMission() {
    const missionId = document.getElementById('update-mission-id').value.trim();
    const status = document.getElementById('update-status');
    
    const updates = {};
    
    const location = document.getElementById('update-location').value.trim();
    if (location) updates.location = location;
    
    const outcome = document.getElementById('update-outcome').value;
    if (outcome) updates.outcome = outcome;
    
    const damageCost = document.getElementById('update-damageCost').value;
    if (damageCost) updates.damageCost = parseInt(damageCost);
    
    const comment = document.getElementById('update-comment').value.trim();
    if (comment) updates.comment = comment;
    
    const addTags = document.getElementById('update-add-tags').value.trim();
    if (addTags) {
        updates.addMissionTags = addTags.split(',').map(t => t.trim()).filter(t => t);
    }
    
    const removeTags = document.getElementById('update-remove-tags').value.trim();
    if (removeTags) {
        updates.removeMissionTags = removeTags.split(',').map(t => t.trim()).filter(t => t);
    }
    
    if (Object.keys(updates).length === 0) {
        status.innerHTML = '<span class="error"> Нет данных для обновления</span>';
        return;
    }
    
    status.innerHTML = '<div class="loading"></div> Отправка...';
    
    try {
        const response = await fetch(`${API_BASE}/${missionId}`, {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(updates)
        });
        
        if (!response.ok) {
            const error = await response.text();
            throw new Error(error);
        }
        
        const result = await response.json();
        status.innerHTML = '<span class="success"> Миссия обновлена успешно!</span>';
        
        document.getElementById('update-location').value = '';
        document.getElementById('update-outcome').value = '';
        document.getElementById('update-damageCost').value = '';
        document.getElementById('update-comment').value = '';
        document.getElementById('update-add-tags').value = '';
        document.getElementById('update-remove-tags').value = '';
        
        loadMissions();
        
    } catch (error) {
        status.innerHTML = `<span class="error"> ${error.message}</span>`;
    }
}

// ===== ВСПЛЫВАЮЩЕЕ СООБЩЕНИЕ =====
function showToast(message, type = 'success') {
    const toast = document.createElement('div');
    toast.className = `toast ${type}`;
    toast.textContent = message;
    document.body.appendChild(toast);
    
    setTimeout(() => {
        toast.classList.add('show');
    }, 10);
    
    setTimeout(() => {
        toast.classList.remove('show');
        setTimeout(() => {
            toast.remove();
        }, 300);
    }, 3000);
}

// ===== ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ =====
function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

// Загружаем список при старте
loadMissions();