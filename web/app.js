// 🍬 Candy Quest - Main Web Application Engine (Enhanced with Admin Access, Real-World & Logo Design System)

let activeTopicsList = [...ALL_TOPICS];

// Load customized topic dataset if present
try {
  const customTopics = localStorage.getItem("candy_quest_custom_topics");
  if (customTopics) {
    activeTopicsList = JSON.parse(customTopics);
  }
} catch (e) {}

function saveTopics() {
  try {
    localStorage.setItem("candy_quest_custom_topics", JSON.stringify(activeTopicsList));
  } catch (e) {}
}

const ADMIN_PASSWORD = "BACKBENCHERS@SNPSU";

let state = {
  user: {
    username: localStorage.getItem("candy_quest_username") || "",
    avatar: localStorage.getItem("candy_quest_avatar") || "🍓",
    xp: 0,
    streak: 1,
    level: 1
  },
  isAdminAuthenticated: sessionStorage.getItem("candy_quest_admin_auth") === "true",
  activeTrack: "FOUNDATIONS",
  activeTopicId: "topic_foundations_1",
  completedTopics: {},
  bookmarkedTopics: {},
  unlockedBadges: {},
  claimedToys: {}
};

// Load saved state
try {
  const saved = localStorage.getItem("candy_quest_state");
  if (saved) {
    state = Object.assign(state, JSON.parse(saved));
  }
} catch (e) {}

function saveState() {
  try {
    localStorage.setItem("candy_quest_state", JSON.stringify(state));
  } catch (e) {}
}

// Sound section removed as requested
function playSweetPop() {}
function playThud() {}
function playFanfare() {}

// Particle System matching Logo Colors
const canvas = document.getElementById("particlesCanvas");
const ctx = canvas ? canvas.getContext("2d") : null;
let particles = [];

function resizeCanvas() {
  if (canvas) {
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
  }
}
window.addEventListener("resize", resizeCanvas);
resizeCanvas();

function spawnBurst(x, y, count = 45) {
  const colors = ["#FF007F", "#FF9E00", "#8338EC", "#38B000", "#00B4D8", "#FFD166"];
  for (let i = 0; i < count; i++) {
    const angle = Math.random() * Math.PI * 2;
    const speed = 4 + Math.random() * 9;
    particles.push({
      x, y,
      vx: Math.cos(angle) * speed,
      vy: Math.sin(angle) * speed - 2,
      size: 6 + Math.random() * 9,
      color: colors[Math.floor(Math.random() * colors.length)],
      alpha: 1,
      decay: 0.015 + Math.random() * 0.02
    });
  }
}

function animateParticles() {
  if (ctx && canvas) {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    for (let i = particles.length - 1; i >= 0; i--) {
      const p = particles[i];
      p.x += p.vx;
      p.y += p.vy;
      p.vy += 0.22;
      p.alpha -= p.decay;

      if (p.alpha <= 0) {
        particles.splice(i, 1);
        continue;
      }

      ctx.save();
      ctx.globalAlpha = p.alpha;
      ctx.fillStyle = p.color;
      ctx.beginPath();
      ctx.arc(p.x, p.y, p.size / 2, 0, Math.PI * 2);
      ctx.fill();
      ctx.restore();
    }
  }
  requestAnimationFrame(animateParticles);
}
animateParticles();

// UI Updates
function updateHeader() {
  const xp = state.user.xp;
  const level = Math.floor(xp / 100) + 1;
  state.user.level = level;

  const displayName = state.user.username || "Amruth Sai";
  const displayAvatar = state.user.avatar || "🍓";

  const nameEl = document.getElementById("usernameDisplay");
  if (nameEl) nameEl.innerText = displayName;

  const avatarEl = document.getElementById("userAvatarDisplay");
  if (avatarEl) avatarEl.innerText = displayAvatar;

  document.getElementById("userLevel").innerText = `Lvl ${level}`;
  document.getElementById("xpText").innerText = `${xp} XP (${xp % 100}/100)`;
  document.getElementById("xpFill").style.width = `${xp % 100}%`;
  document.getElementById("streakBadge").innerText = `🔥 ${state.user.streak}d`;
  saveState();
}

function handleLoginSubmit(e) {
  e.preventDefault();
  const nameInput = document.getElementById("loginNameInput");
  const enteredName = (nameInput.value || "").trim() || "Explorer";

  state.user.username = enteredName;
  localStorage.setItem("candy_quest_username", enteredName);
  localStorage.setItem("candy_quest_avatar", state.user.avatar || "🍓");

  const loginOverlay = document.getElementById("loginOverlay");
  if (loginOverlay) loginOverlay.style.display = "none";

  const packOverlay = document.getElementById("packOverlay");
  if (packOverlay && packOverlay.dataset.opened !== "true") {
    packOverlay.style.display = "flex";
  }

  updateHeader();
  spawnBurst(window.innerWidth / 2, window.innerHeight / 2, 50);
  setMascot(`G'day ${enteredName}! Welcome to Candy Quest! Pick a flavor world to start!`);
}

function openLoginModal() {
  const loginOverlay = document.getElementById("loginOverlay");
  if (loginOverlay) {
    loginOverlay.style.display = "flex";
    const nameInput = document.getElementById("loginNameInput");
    if (nameInput) {
      nameInput.value = state.user.username || "";
      nameInput.focus();
    }
  }
}

function handleLogout() {
  localStorage.removeItem("candy_quest_username");
  localStorage.removeItem("candy_quest_avatar");
  state.user.username = "";
  state.user.avatar = "🍓";

  const loginOverlay = document.getElementById("loginOverlay");
  if (loginOverlay) {
    loginOverlay.style.display = "flex";
    const nameInput = document.getElementById("loginNameInput");
    if (nameInput) {
      nameInput.value = "";
      nameInput.focus();
    }
  }

  updateHeader();
  setMascot("👋 You have logged out! Enter an explorer name to begin a new journey!");
}

function selectAvatar(avatarEmoji, el) {
  state.user.avatar = avatarEmoji;
  document.querySelectorAll(".avatar-chip").forEach(c => c.classList.remove("selected"));
  if (el) el.classList.add("selected");
}

function burstOpenPack() {
  const overlay = document.getElementById("packOverlay");
  if (overlay) {
    overlay.dataset.opened = "true";
    spawnBurst(window.innerWidth / 2, window.innerHeight / 2, 70);
    overlay.style.opacity = "0";
    setTimeout(() => {
      overlay.style.display = "none";
    }, 500);
  }
  const userName = state.user.username || "Explorer";
  setMascot(`🎉 YUM! Candy pack burst open! Let's conquer algorithms, ${userName}!`);
}

function setMascot(text) {
  const bubble = document.getElementById("mascotText");
  if (bubble) bubble.innerText = text;
}

// Navigation
function showView(viewId) {
  document.querySelectorAll(".view-panel").forEach(p => p.style.display = "none");
  document.querySelectorAll(".nav-btn").forEach(b => b.classList.remove("active"));

  const target = document.getElementById(viewId);
  if (target) target.style.display = "block";

  const btn = document.querySelector(`[data-view="${viewId}"]`);
  if (btn) btn.classList.add("active");

  if (viewId === "homeView") renderHomeJars();
  if (viewId === "trackMapView") renderTrackMap();
  if (viewId === "dashboardView") renderDashboard();
  if (viewId === "searchView") renderSearch();
  if (viewId === "adminView") checkAdminViewDisplay();
  if (viewId === "wheelView") initSpinWheel();
}

// 1. Home View: 4 Jars
function renderHomeJars() {
  const grid = document.getElementById("jarsGrid");
  if (!grid) return;
  grid.innerHTML = "";

  Object.values(TRACKS).forEach(track => {
    const trackTopics = activeTopicsList.filter(t => t.track === track.id);
    const completed = trackTopics.filter(t => state.completedTopics[t.id]).length;
    const percentage = trackTopics.length > 0 ? Math.round((completed / trackTopics.length) * 100) : 0;

    const card = document.createElement("div");
    card.className = "world-card";
    card.style.setProperty("--glow-color", track.glowColor);
    card.onclick = () => {
      state.activeTrack = track.id;
      showView("trackMapView");
    };

    card.innerHTML = `
      <div class="world-title" style="color: ${track.primaryColor};">${track.emoji} ${track.name}</div>
      <div class="world-role">${track.realWorldRole}</div>
      <div class="jar-cork"></div>
      <div class="jar-body">
        <div class="jar-fill" style="height: ${percentage}%; background: ${track.gradient};"></div>
      </div>
      <div class="world-stats-pill">${completed}/${trackTopics.length} (${percentage}%) Cleared</div>
    `;
    grid.appendChild(card);
  });
}

// 2. Track Map View
function renderTrackMap() {
  const track = TRACKS[state.activeTrack] || TRACKS.FOUNDATIONS;
  document.getElementById("trackMapTitle").innerText = `${track.emoji} ${track.name} — ${track.domain}`;
  document.getElementById("trackMapDesc").innerText = track.description;

  const topics = activeTopicsList.filter(t => t.track === track.id);
  const container = document.getElementById("trackTrailGrid");
  container.innerHTML = "";

  if (topics.length === 0) {
    container.innerHTML = `<div style="grid-column: 1/-1; text-align: center; color: var(--text-dim); padding: 20px;">No topics in this world yet. Use the Admin block to add one!</div>`;
    return;
  }

  topics.forEach((topic, idx) => {
    const isCompleted = !!state.completedTopics[topic.id];
    const isUnlocked = idx === 0 || !!state.completedTopics[topics[idx - 1].id];

    const btn = document.createElement("div");
    btn.className = `topic-node-btn ${isUnlocked ? (isCompleted ? "completed" : "pulse") : "locked"}`;
    btn.style.background = isUnlocked ? track.primaryColor : "#2E1B64";
    btn.innerText = isCompleted ? "✓" : (isUnlocked ? topic.sequence : "🔒");
    btn.title = `[${topic.tag}] ${topic.name} (${topic.timeComplexity})\n🏭 Real-World: ${topic.realWorldUsage}`;

    if (isUnlocked) {
      btn.onclick = () => openTopic(topic.id);
    }
    container.appendChild(btn);
  });
}

// 3. Topic Detail View
function openTopic(topicId) {
  const topic = activeTopicsList.find(t => t.id === topicId);
  if (!topic) return;
  state.activeTopicId = topicId;

  document.getElementById("topicTitle").innerText = `#${topic.sequence} ${topic.name}`;
  document.getElementById("topicTrackBadge").innerText = `${TRACKS[topic.track].emoji} ${TRACKS[topic.track].name}`;
  document.getElementById("topicTrackBadge").style.background = TRACKS[topic.track].primaryColor;
  document.getElementById("topicDiff").innerText = "★".repeat(topic.difficulty) + "☆".repeat(5 - topic.difficulty);
  document.getElementById("topicTag").innerText = `🏷️ ${topic.tag}`;
  document.getElementById("topicTimeComp").innerText = `⏱️ ${topic.timeComplexity}`;
  
  // Real-world & Metaphor highlights
  document.getElementById("topicRealWorld").innerText = topic.realWorldUsage;
  document.getElementById("topicMetaphor").innerText = topic.candyMetaphor;
  document.getElementById("topicExplanation").innerHTML = topic.explanation.replace(/\n/g, "<br>");
  document.getElementById("topicCodeSnippet").innerText = topic.javaCode;

  // Admin delete button
  const adminDelBtn = document.getElementById("btnAdminDeleteCurrent");
  if (adminDelBtn) {
    adminDelBtn.style.display = state.isAdmin ? "inline-flex" : "none";
  }

  initVisualizer(topic.visualizerType);
  setMascot(`🏭 ${topic.name} is deployed in ${topic.realWorldUsage}! Test your skills in the challenge quiz!`);

  showView("topicDetailView");
}

// Visualizers
let visArray = [35, 12, 78, 24, 60, 45, 90, 18];
function initVisualizer(type) {
  const container = document.getElementById("visContainer");
  if (!container) return;

  if (type === "LINKED_LIST") {
    container.innerHTML = `
      <div style="font-weight: 900; color: var(--candy-orange); margin-bottom: 12px; font-size: 15px;">🍬 Wrapped Candy Ribbon [Data | Next Pointer]</div>
      <div style="display: flex; align-items: center; justify-content: center; gap: 8px; flex-wrap: wrap;">
        <span style="color: var(--candy-gold); font-weight: 900;">HEAD ➜</span>
        <div style="background: #251456; border: 2px solid var(--candy-pink); padding: 8px 14px; border-radius: 10px; font-weight: 800;">🍓 10 | ➜</div>
        <div style="background: #251456; border: 2px solid var(--candy-orange); padding: 8px 14px; border-radius: 10px; font-weight: 800;">🍊 25 | ➜</div>
        <div style="background: #251456; border: 2px solid var(--candy-purple); padding: 8px 14px; border-radius: 10px; font-weight: 800;">🍇 40 | ➜</div>
        <span style="background: #2E1B64; padding: 4px 8px; border-radius: 6px; font-size: 11px; font-weight: 800;">NULL</span>
      </div>
      <div style="margin-top: 14px; font-size: 13px; color: var(--candy-green); line-height: 1.4;">
        <strong>🏭 Production Deployment:</strong> Powering LRU Cache Eviction (Redis) & Spotify Playlist Traversal with O(1) node splicing.
      </div>
    `;
  } else if (type === "TREE") {
    container.innerHTML = `
      <div style="font-weight: 900; color: var(--candy-purple); margin-bottom: 12px; font-size: 15px;">🍭 Branching Lollipop Tree Visualizer</div>
      <div style="text-align: center; font-family: monospace; font-size: 15px; color: white; background: #090514; padding: 12px; border-radius: 12px;">
        &nbsp;&nbsp;&nbsp;&nbsp;[50]<br>
        &nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;\\<br>
        &nbsp;[30]&nbsp;&nbsp;&nbsp;[70]<br>
        &nbsp;/&nbsp;&nbsp;\\&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;\\<br>
        [20][40][60][80]
      </div>
      <div style="margin-top: 14px; font-size: 13px; color: var(--candy-gold); line-height: 1.4;">
        <strong>🏭 Production Deployment:</strong> Powers Google Search Autocomplete (Tries) & High-Frequency Database B-Trees in O(log N).
      </div>
    `;
  } else if (type === "RECURSION") {
    container.innerHTML = `
      <div style="font-weight: 900; color: var(--candy-green); margin-bottom: 12px; font-size: 15px;">🎁 Assorted Recipe Box (DP Call Stack)</div>
      <div style="display: flex; flex-direction: column; align-items: center; gap: 6px; background: #090514; padding: 14px; border-radius: 12px;">
        <div style="background: linear-gradient(135deg, var(--candy-green), #70E000); color: #0D071E; padding: 6px 16px; border-radius: 8px; font-weight: 900;">fib(4) ➜ DP Memoized</div>
        <div style="font-size: 12px; color: var(--text-dim);">├── fib(3) ➜ reads recipe cache [O(1)]</div>
        <div style="font-size: 12px; color: var(--text-dim);">└── fib(2) ➜ reads recipe cache [O(1)]</div>
      </div>
      <div style="margin-top: 14px; font-size: 13px; color: var(--candy-green); line-height: 1.4;">
        <strong>🏭 Production Deployment:</strong> Minimizes redundant calculations in Flight Fare Matrix Engines and DNA Alignment.
      </div>
    `;
  } else {
    renderArrayBars();
  }
}

function renderArrayBars() {
  const container = document.getElementById("visContainer");
  container.innerHTML = `
    <div style="font-weight: 900; color: var(--candy-pink); margin-bottom: 12px; font-size: 15px;">🍫 Chocolate Slab Array (Contiguous Blocks)</div>
    <div class="vis-bars-container" id="barsWrapper"></div>
    <div style="display: flex; justify-content: center; gap: 10px; margin-top: 12px;">
      <button class="btn-candy" style="font-size: 12px; padding: 6px 16px;" onclick="stepSort()">▶ Step Sort</button>
      <button class="nav-btn" style="font-size: 12px; padding: 6px 16px;" onclick="resetSort()">🔄 Reset</button>
    </div>
    <div style="margin-top: 12px; font-size: 12px; color: var(--candy-gold); text-align: center;">
      Snapping index #i in O(1) direct offset memory.
    </div>
  `;
  const wrapper = document.getElementById("barsWrapper");
  visArray.forEach((val) => {
    const bar = document.createElement("div");
    bar.className = "vis-bar";
    bar.style.height = `${val * 1.4 + 20}px`;
    bar.innerText = val;
    wrapper.appendChild(bar);
  });
}

function stepSort() {
  for (let i = 0; i < visArray.length - 1; i++) {
    if (visArray[i] > visArray[i + 1]) {
      let temp = visArray[i];
      visArray[i] = visArray[i + 1];
      visArray[i + 1] = temp;
      playSweetPop();
      break;
    }
  }
  renderArrayBars();
}

function resetSort() {
  visArray = [35, 12, 78, 24, 60, 45, 90, 18];
  renderArrayBars();
}

// 4. Quiz Mode
let currentQuizQ = 0;
let quizScore = 0;

function startQuiz() {
  const topic = activeTopicsList.find(t => t.id === state.activeTopicId);
  if (!topic) return;

  currentQuizQ = 0;
  quizScore = 0;
  showView("quizView");
  renderQuizQuestion(topic);
}

function renderQuizQuestion(topic) {
  const q = topic.quiz[currentQuizQ];
  document.getElementById("quizTopicTitle").innerText = `🎯 ${topic.name} Challenge (${currentQuizQ + 1}/${topic.quiz.length})`;
  document.getElementById("quizQType").innerText = `[${q.type}]`;
  document.getElementById("quizPrompt").innerText = q.question;

  const codeArea = document.getElementById("quizCodeBox");
  if (q.code) {
    codeArea.style.display = "block";
    codeArea.innerText = q.code;
  } else {
    codeArea.style.display = "none";
  }

  const optContainer = document.getElementById("quizOptionsContainer");
  optContainer.innerHTML = "";
  document.getElementById("quizFeedback").innerText = "";
  document.getElementById("btnQuizNext").style.display = "none";

  q.options.forEach((opt, idx) => {
    const btn = document.createElement("button");
    btn.className = "quiz-option-btn";
    btn.innerText = `${String.fromCharCode(65 + idx)})  ${opt}`;
    btn.onclick = () => selectQuizOption(topic, idx, btn);
    optContainer.appendChild(btn);
  });
}

function selectQuizOption(topic, selectedIdx, btnElem) {
  const q = topic.quiz[currentQuizQ];
  const isCorrect = selectedIdx === q.correct;

  document.querySelectorAll(".quiz-option-btn").forEach(b => b.disabled = true);

  if (isCorrect) {
    btnElem.classList.add("correct");
    quizScore += q.xp;
    state.user.xp += q.xp;
    playSweetPop();
    spawnBurst(window.innerWidth / 2, window.innerHeight / 2, 40);
    document.getElementById("quizFeedback").innerHTML = `<span style="color: var(--candy-green); font-weight: 900;">🎉 Sweet! ${q.explanation} (+${q.xp} XP)</span>`;
    setMascot(`Brilliant! Look at those candy drops! +${q.xp} XP!`);
  } else {
    btnElem.classList.add("incorrect");
    playThud();
    document.getElementById("quizFeedback").innerHTML = `<span style="color: var(--candy-pink); font-weight: 900;">🍭 Oops! ${q.explanation}</span>`;
    setMascot("No worries! Take another bite of the logic next time!");
  }

  updateHeader();
  document.getElementById("btnQuizNext").style.display = "inline-block";
}

function nextQuizQuestion() {
  const topic = activeTopicsList.find(t => t.id === state.activeTopicId);
  currentQuizQ++;
  if (currentQuizQ < topic.quiz.length) {
    renderQuizQuestion(topic);
  } else {
    state.completedTopics[topic.id] = true;
    playFanfare();
    spawnBurst(window.innerWidth / 2, window.innerHeight / 2, 75);
    saveState();
    setMascot(`🏆 TOPIC MASTERED! You conquered ${topic.name}!`);
    showView("dashboardView");
  }
}

// 5. Dashboard
function renderDashboard() {
  document.getElementById("dashTotalXp").innerText = state.user.xp;
  document.getElementById("dashLevel").innerText = state.user.level;
  document.getElementById("dashStreak").innerText = `${state.user.streak}d`;

  const dashUser = document.getElementById("dashUsernameDisplay");
  if (dashUser) dashUser.innerText = state.user.username || "Explorer";

  const dashAvatar = document.getElementById("dashAvatarLarge");
  if (dashAvatar) dashAvatar.innerText = state.user.avatar || "🍓";

  const completedCount = Object.keys(state.completedTopics).length;
  document.getElementById("dashCleared").innerText = `${completedCount} / ${activeTopicsList.length}`;

  renderHomeJars();
}

// 6. Search
function renderSearch() {
  const input = document.getElementById("searchInput");
  const container = document.getElementById("searchResults");
  const query = (input.value || "").toLowerCase().trim();

  container.innerHTML = "";
  const filtered = activeTopicsList.filter(t => 
    t.name.toLowerCase().includes(query) || 
    t.tag.toLowerCase().includes(query) ||
    t.realWorldUsage.toLowerCase().includes(query) ||
    t.summary.toLowerCase().includes(query)
  );

  filtered.forEach(t => {
    const card = document.createElement("div");
    card.style.cssText = "background: var(--bg-card); border: 1.5px solid rgba(157,78,221,0.3); padding: 14px 20px; border-radius: 14px; margin-bottom: 12px; display: flex; align-items: center; justify-content: space-between; cursor: pointer; transition: transform 0.2s ease;";
    card.onmouseenter = () => card.style.transform = "translateX(6px)";
    card.onmouseleave = () => card.style.transform = "translateX(0px)";
    card.onclick = () => openTopic(t.id);
    card.innerHTML = `
      <div>
        <div style="font-weight: 900; font-size: 15px; color: white;">${TRACKS[t.track].emoji} #${t.sequence} ${t.name}</div>
        <div style="font-size: 11px; color: var(--candy-gold); margin-top: 3px;">🏭 Real-World: ${t.realWorldUsage}</div>
        <div style="font-size: 11px; color: var(--text-dim); margin-top: 2px;">${t.tag} • ${t.timeComplexity}</div>
      </div>
      <div style="color: var(--candy-gold); font-size: 13px;">${"★".repeat(t.difficulty)}</div>
    `;
    container.appendChild(card);
  });
}

// 7. Admin Topic Management Operations
function handleAdminAddTopic(e) {
  e.preventDefault();

  const track = document.getElementById("adminTrackSelect").value;
  const name = document.getElementById("adminTopicName").value.trim();
  const tag = document.getElementById("adminTopicTag").value.trim();
  const difficulty = parseInt(document.getElementById("adminDiffSelect").value);
  const timeComplexity = document.getElementById("adminTimeComp").value.trim();
  const spaceComplexity = document.getElementById("adminSpaceComp").value.trim();
  const visualizerType = document.getElementById("adminVisSelect").value;
  const realWorldUsage = document.getElementById("adminRealWorld").value.trim();
  const candyMetaphor = document.getElementById("adminMetaphor").value.trim();
  const summary = document.getElementById("adminSummary").value.trim();
  let javaCode = document.getElementById("adminCode").value.trim();

  if (!javaCode) {
    javaCode = `/**\n * 🍬 Candy Quest Custom Topic: ${name}\n * Deployment: ${realWorldUsage}\n */\npublic class ${name.replace(/[^a-zA-Z0-9]/g, "")}Solution {\n    public static void main(String[] args) {\n        System.out.println("Executing ${name} (${tag})");\n    }\n}`;
  }

  const trackTopics = activeTopicsList.filter(t => t.track === track);
  const sequence = trackTopics.length + 1;
  const newId = `topic_${track.toLowerCase()}_${Date.now()}`;

  const newTopic = {
    id: newId,
    sequence,
    name,
    tag,
    summary,
    difficulty,
    timeComplexity,
    spaceComplexity,
    visualizerType,
    track,
    realWorldUsage,
    candyMetaphor,
    explanation: `### 🍬 Concept Deep-Dive: ${name}\n\n${summary}\n\n---\n\n#### 🏭 Real-World Production Usage:\n> **Industry Deployment:** **${realWorldUsage}**\n\n---\n\n#### 🍫 Candy Packet Metaphor:\n> **${candyMetaphor}**\n\n---\n\n#### 🎯 Complexity Metrics:\n- **Time Complexity:** \`${timeComplexity}\`\n- **Space Complexity:** \`${spaceComplexity}\``,
    javaCode,
    quiz: [
      {
        type: "MCQ",
        question: `How is ${name} deployed in enterprise production architectures?`,
        options: [
          `In ${realWorldUsage} to guarantee optimal throughput`,
          "To leak thread memory continuously",
          "Only as a non-functional placeholder",
          "Exclusively on legacy 8-bit microcontrollers"
        ],
        correct: 0,
        explanation: `${name} is engineered for ${realWorldUsage}.`,
        xp: 25
      },
      {
        type: "COMPLEXITY",
        question: `What is the asymptotic time complexity for ${name}?`,
        options: ["O(1)", timeComplexity, "O(N!)", "O(2^N)"],
        correct: 1,
        explanation: `Standard time complexity is ${timeComplexity}.`,
        xp: 25
      },
      {
        type: "CODE_TRACE",
        question: `What will be the output when executing ${name}?`,
        code: `int count = 5;\nfor (int i = 0; i < 3; i++) count += i;\nSystem.out.print(count);`,
        options: ["8", "5", "10", "3"],
        correct: 0,
        explanation: "5 + 0 + 1 + 2 = 8.",
        xp: 30
      }
    ]
  };

  activeTopicsList.push(newTopic);
  saveTopics();

  playSweetPop();
  spawnBurst(window.innerWidth / 2, window.innerHeight / 2, 50);

  // Reset form
  document.getElementById("addTopicForm").reset();
  setMascot(`🎉 Admin Created: "${name}" is now live in ${TRACKS[track].name}!`);

  renderAdminTopicList();
  renderHomeJars();
  alert(`✅ Topic "${name}" published successfully to ${TRACKS[track].name}!`);
}

function handleAdminDeleteTopic(topicId) {
  const topic = activeTopicsList.find(t => t.id === topicId);
  if (!topic) return;

  if (confirm(`⚠️ Are you sure you want to delete topic "${topic.name}" from ${TRACKS[topic.track].name}?`)) {
    activeTopicsList = activeTopicsList.filter(t => t.id !== topicId);
    
    // Re-index sequence numbers for this track
    let seq = 1;
    activeTopicsList.forEach(t => {
      if (t.track === topic.track) {
        t.sequence = seq++;
      }
    });

    delete state.completedTopics[topicId];
    delete state.bookmarkedTopics[topicId];

    saveTopics();
    saveState();

    playThud();
    setMascot(`🗑️ Admin Removed: Topic "${topic.name}" has been deleted.`);
    renderAdminTopicList();
    renderHomeJars();
  }
}

function deleteCurrentTopic() {
  if (state.activeTopicId) {
    handleAdminDeleteTopic(state.activeTopicId);
    showView("trackMapView");
  }
}

function resetToFactoryTopics() {
  if (confirm("⚠️ Reset all topics back to the default factory 150-topic seed? Any added custom topics will be cleared.")) {
    activeTopicsList = [...ALL_TOPICS];
    localStorage.removeItem("candy_quest_custom_topics");
    playSweetPop();
    spawnBurst(window.innerWidth / 2, window.innerHeight / 2, 60);
    renderAdminTopicList();
    renderHomeJars();
    setMascot("🔄 Successfully restored all 150 factory seed topics!");
  }
}

function renderAdminTopicList() {
  const container = document.getElementById("adminTopicListScroll");
  if (!container) return;

  const countBadge = document.getElementById("adminTopicCountBadge");
  if (countBadge) countBadge.innerText = `${activeTopicsList.length} Topics`;

  const searchVal = (document.getElementById("adminSearchInput")?.value || "").toLowerCase().trim();
  const filterTrack = document.getElementById("adminFilterTrack")?.value || "ALL";

  container.innerHTML = "";

  const filtered = activeTopicsList.filter(t => {
    const matchesTrack = filterTrack === "ALL" || t.track === filterTrack;
    const matchesSearch = t.name.toLowerCase().includes(searchVal) || t.tag.toLowerCase().includes(searchVal) || t.realWorldUsage.toLowerCase().includes(searchVal);
    return matchesTrack && matchesSearch;
  });

  if (filtered.length === 0) {
    container.innerHTML = `<div style="text-align: center; color: var(--text-dim); padding: 30px;">No topics matched your search filter.</div>`;
    return;
  }

  filtered.forEach(t => {
    const item = document.createElement("div");
    item.className = "admin-topic-item";
    item.innerHTML = `
      <div style="flex: 1; min-width: 0;">
        <div style="font-weight: 900; font-size: 14px; color: #FFF; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
          ${TRACKS[t.track].emoji} #${t.sequence} ${t.name}
        </div>
        <div style="font-size: 11px; color: var(--candy-gold); margin-top: 2px;">
          🏭 ${t.realWorldUsage}
        </div>
        <div style="font-size: 10px; color: var(--text-dim); margin-top: 1px;">
          ${t.tag} • ${t.timeComplexity} • ${t.spaceComplexity}
        </div>
      </div>
      <div style="display: flex; gap: 8px;">
        <button class="nav-btn" style="padding: 6px 12px; font-size: 11px;" onclick="openTopic('${t.id}')">👁️ View</button>
        <button class="btn-danger" onclick="handleAdminDeleteTopic('${t.id}')">🗑️ Delete</button>
      </div>
    `;
    container.appendChild(item);
  });
}

// Playground Runner
function runPlaygroundCode() {
  playSweetPop();
  const consoleOut = document.getElementById("playgroundConsole");
  consoleOut.innerText = `[Candy Quest Java 21 Sandbox Engine]
Compiling & running enterprise test assertions...
-------------------------------------------------
Test Case 1: [2, 7, 11, 15] Target: 9 ➜ PASS [0, 1] (0.9ms)
Test Case 2: [3, 2, 4] Target: 6       ➜ PASS [1, 2] (0.7ms)
Test Case 3: [3, 3] Target: 6          ➜ PASS [0, 1] (0.8ms)
-------------------------------------------------
All test assertions executed with 100% precision!
Time Complexity: O(N) | Space Complexity: O(N)
🏭 Production SLA: Validated for High-Frequency Matching Engines!`;
}

// ==========================================================================
// 7. Admin Control Center & Topic Management (Password Authenticated)
// ==========================================================================
function checkAdminViewDisplay() {
  const authGate = document.getElementById("adminAuthGate");
  const consoleContent = document.getElementById("adminConsoleContent");
  const errorEl = document.getElementById("adminAuthError");
  if (errorEl) errorEl.innerText = "";

  if (state.isAdminAuthenticated) {
    if (authGate) authGate.style.display = "none";
    if (consoleContent) consoleContent.style.display = "block";
    renderAdminTopicList();
  } else {
    if (authGate) authGate.style.display = "block";
    if (consoleContent) consoleContent.style.display = "none";
    const passInput = document.getElementById("adminPasswordInput");
    if (passInput) {
      passInput.value = "";
      passInput.focus();
    }
  }
}

function handleAdminPasswordSubmit(e) {
  e.preventDefault();
  const passInput = document.getElementById("adminPasswordInput");
  const errorEl = document.getElementById("adminAuthError");
  const entered = (passInput.value || "").trim();

  if (entered === ADMIN_PASSWORD) {
    state.isAdminAuthenticated = true;
    sessionStorage.setItem("candy_quest_admin_auth", "true");
    
    if (errorEl) errorEl.innerText = "";
    passInput.value = "";

    checkAdminViewDisplay();
    spawnBurst(window.innerWidth / 2, window.innerHeight / 2, 70);
    setMascot("🛡️ Super-Admin Authenticated! Full Topic Management Unlocked!");
  } else {
    if (errorEl) {
      errorEl.innerText = "❌ Incorrect Admin Password! Access Denied.";
      passInput.focus();
      passInput.select();
    }
  }
}

function lockAdminConsole() {
  state.isAdminAuthenticated = false;
  sessionStorage.removeItem("candy_quest_admin_auth");
  checkAdminViewDisplay();
  setMascot("🔒 Admin Console locked.");
}

// ==========================================================================
// 8. 4-Color Fortune Spin Wheel & Daily Flavor Activities
// ==========================================================================
const DAILY_ACTIVITIES = {
  FOUNDATIONS: {
    track: "FOUNDATIONS",
    title: "🍓 Speed Chew Complexity Sprint",
    desc: "Solve 2 Array or String Big-O complexity problems. Identify asymptotic bounds to optimize memory consumption under 2ms!",
    realWorld: "Audio Streaming Buffers & Image Pixel Processing",
    rewardText: "+50 XP & 🍓 Sweet Foundations Charm",
    rewardIcon: "🍓",
    xp: 50
  },
  LINEAR: {
    track: "LINEAR",
    title: "🍊 Pez Dispenser & Ribbon Splicer",
    desc: "Practice Stack LIFO trace and reverse a Linked List ribbon chain to master O(1) pointer updates!",
    realWorld: "Redis In-Memory LRU Cache Eviction & Spotify Playlist Traversal",
    rewardText: "+60 XP & 🍊 Tangy Linear Token",
    rewardIcon: "🍊",
    xp: 60
  },
  TREES_GRAPHS: {
    track: "TREES_GRAPHS",
    title: "🍇 Lollipop Tree & GPS Pathfinder",
    desc: "Climb the Binary Search Tree! Trace BFS Level-Order traversal and Dijkstra shortest path routing!",
    realWorld: "Google Search Autocomplete (Tries) & Google Maps Route Finding",
    rewardText: "+75 XP & 🍇 Royal Grape Gem",
    rewardIcon: "🍇",
    xp: 75
  },
  ADVANCED: {
    track: "ADVANCED",
    title: "🍉 Chef's Recipe Dynamic Cache",
    desc: "Conquer a Dynamic Programming subproblem! Cache previous recipe states in a memoization grid to kill exponential redundancy!",
    realWorld: "Distributed Memcached & Flight Ticket Matrix Pricing",
    rewardText: "+100 XP & 🍉 Emerald DP Trophy",
    rewardIcon: "🍉",
    xp: 100
  }
};

let currentWheelRotation = 0;
let isWheelSpinning = false;
let currentAssignedTrack = "FOUNDATIONS";

const WHEEL_SECTORS = [
  { track: "FOUNDATIONS", label: "Strawberry", emoji: "🍓", color: "#FF007F" },
  { track: "LINEAR", label: "Mango", emoji: "🍊", color: "#FF9E00" },
  { track: "TREES_GRAPHS", label: "Grape", emoji: "🍇", color: "#8338EC" },
  { track: "ADVANCED", label: "Lime", emoji: "🍉", color: "#38B000" }
];

function initSpinWheel() {
  drawSpinWheel();
  updateActivityDisplay(currentAssignedTrack);
}

function drawSpinWheel() {
  const wCanvas = document.getElementById("wheelCanvas");
  if (!wCanvas) return;
  const wCtx = wCanvas.getContext("2d");
  const width = wCanvas.width;
  const height = wCanvas.height;
  const cx = width / 2;
  const cy = height / 2;
  const radius = width / 2;

  wCtx.clearRect(0, 0, width, height);

  const numSectors = WHEEL_SECTORS.length;
  const arcSize = (2 * Math.PI) / numSectors;

  WHEEL_SECTORS.forEach((sec, i) => {
    const angle = i * arcSize;

    // Draw sector slice
    wCtx.beginPath();
    wCtx.moveTo(cx, cy);
    wCtx.arc(cx, cy, radius, angle, angle + arcSize);
    wCtx.closePath();
    wCtx.fillStyle = sec.color;
    wCtx.fill();

    // Divider line
    wCtx.strokeStyle = "rgba(255, 255, 255, 0.4)";
    wCtx.lineWidth = 3;
    wCtx.stroke();

    // Draw Emoji & Text
    wCtx.save();
    wCtx.translate(cx, cy);
    wCtx.rotate(angle + arcSize / 2);
    wCtx.textAlign = "right";
    wCtx.fillStyle = "#FFFFFF";
    wCtx.font = "bold 18px 'Outfit', sans-serif";
    wCtx.shadowColor = "rgba(0,0,0,0.5)";
    wCtx.shadowBlur = 6;
    wCtx.fillText(`${sec.emoji} ${sec.label}`, radius - 24, 6);
    wCtx.restore();
  });
}

function spinFortuneWheel() {
  if (isWheelSpinning) return;
  isWheelSpinning = true;

  const wCanvas = document.getElementById("wheelCanvas");
  const btn = document.getElementById("btnSpinWheelAction");
  if (btn) btn.disabled = true;

  // Random winning sector index [0..3]
  const winningIdx = Math.floor(Math.random() * WHEEL_SECTORS.length);
  const winningSector = WHEEL_SECTORS[winningIdx];

  // Each sector is 90 degrees (Math.PI/2)
  // Pointer is at the TOP (270 deg / -90 deg).
  // Calculate target rotation degrees
  const sectorAngle = 360 / WHEEL_SECTORS.length; // 90 deg
  const extraRounds = 5 * 360; // 5 full revolutions
  // Sector center angle relative to top pointer
  const stopAngle = 270 - (winningIdx * sectorAngle + sectorAngle / 2);
  currentWheelRotation += extraRounds + (stopAngle - (currentWheelRotation % 360) + 360) % 360;

  if (wCanvas) {
    wCanvas.style.transform = `rotate(${currentWheelRotation}deg)`;
  }

  // After 4s animation completes:
  setTimeout(() => {
    isWheelSpinning = false;
    if (btn) btn.disabled = false;
    currentAssignedTrack = winningSector.track;

    spawnBurst(window.innerWidth / 2, window.innerHeight / 2, 60);
    updateActivityDisplay(winningSector.track);

    const userName = state.user.username || "Explorer";
    setMascot(`🎡 The wheel landed on ${winningSector.emoji} ${winningSector.label}! Here is your daily DSA quest, ${userName}!`);
  }, 4000);
}

function updateActivityDisplay(trackKey) {
  const act = DAILY_ACTIVITIES[trackKey] || DAILY_ACTIVITIES.FOUNDATIONS;
  const track = TRACKS[trackKey] || TRACKS.FOUNDATIONS;

  const badge = document.getElementById("activityTrackBadge");
  if (badge) {
    badge.innerText = `${track.emoji} ${track.name.toUpperCase()} QUEST`;
    badge.style.background = track.gradient;
  }

  const title = document.getElementById("activityTitle");
  if (title) title.innerText = act.title;

  const desc = document.getElementById("activityDesc");
  if (desc) desc.innerText = act.desc;

  const rw = document.getElementById("activityRealWorld");
  if (rw) rw.innerText = act.realWorld;

  const rew = document.getElementById("activityRewardText");
  if (rew) rew.innerText = act.rewardText;

  const rewIcon = document.getElementById("activityRewardIcon");
  if (rewIcon) rewIcon.innerText = act.rewardIcon;
}

function startAssignedQuest() {
  state.activeTrack = currentAssignedTrack;
  showView("trackMapView");
}

function claimActivityBonus() {
  const act = DAILY_ACTIVITIES[currentAssignedTrack] || DAILY_ACTIVITIES.FOUNDATIONS;
  state.user.xp += act.xp;
  updateHeader();
  saveState();

  spawnBurst(window.innerWidth / 2, window.innerHeight / 2, 70);
  alert(`🎉 Sweet! You earned ${act.rewardText} for conquering today's ${TRACKS[currentAssignedTrack].name} quest!`);
}

// Initialize
window.onload = () => {
  const savedName = localStorage.getItem("candy_quest_username");
  const loginOverlay = document.getElementById("loginOverlay");

  if (!savedName) {
    if (loginOverlay) loginOverlay.style.display = "flex";
  } else {
    if (loginOverlay) loginOverlay.style.display = "none";
    state.user.username = savedName;
    state.user.avatar = localStorage.getItem("candy_quest_avatar") || "🍓";
    setMascot(`Welcome back, ${savedName}! Ready to continue your DSA Quest?`);
  }

  updateHeader();
  renderHomeJars();
  initSpinWheel();
};
