# 🏏 Match Simulator

> A full-stack cricket match simulation platform built with a Java microservices backend and an Angular 20 frontend — delivering real-time match simulation, live scoring, team management, and player tracking.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Angular](https://img.shields.io/badge/Angular_20-DD0031?style=for-the-badge&logo=angular&logoColor=white)
![Microservices](https://img.shields.io/badge/Architecture-Microservices-blue?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Stable-brightgreen?style=for-the-badge)

---

## 📖 Overview

**Match Simulator** is a distributed cricket simulation system that models match scenarios through dedicated microservices. Each service owns a specific domain — teams, players, scoring, and simulation — communicating together to run complete cricket matches from toss to final ball.

The Angular 20 frontend provides a live, reactive interface for match control, scorecards, and player stats.

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────┐
│                  Angular 20 Frontend                │
└────────────────────┬────────────────────────────────┘
                     │ HTTP / REST
      ┌──────────────▼──────────────┐
      │        match_service        │  ← Orchestrates the match flow
      └──┬──────────┬──────────┬───┘
         │          │          │
    ┌────▼───┐  ┌───▼────┐  ┌─▼──────────────┐
    │ Team   │  │ players│  │ ScoringService │
    │Service │  │        │  │                │
    └────────┘  └────────┘  └───────┬────────┘
                                    │
                            ┌───────▼──────┐
                            │  simulation  │  ← Core simulation engine
                            └──────────────┘
```

---

## 🧩 Services

| Service          | Description |
|------------------|------------|
| `match_service`  | Orchestrates the overall match lifecycle — toss, innings, result |
| `ScoringService` | Tracks runs, wickets, overs, extras, and fall of wickets |
| `TeamService`    | Manages team creation, squad selection, and lineup |
| `players`        | Stores and serves player profiles, attributes, and statistics |
| `simulation`     | Core engine — computes ball-by-ball outcomes using player ratings |

---

## ✨ Features

- 🎯 **Ball-by-ball simulation** driven by player skill ratings  
- 📊 **Live scorecard** with batting, bowling, and partnership stats  
- 👥 **Team & squad management** via the TeamService  
- 🧑 **Player profiles** with detailed performance attributes  
- 🔄 **Match lifecycle control** — start, pause, and resume matches  
- 📱 **Reactive Angular 20 UI** with real-time score updates  

---

## 🚀 Getting Started

### Prerequisites

| Tool        | Version |
|-------------|--------|
| Java        | 17+    |
| Maven       | Latest |
| Node.js     | 20+    |
| Angular CLI | 20.x (`npm install -g @angular/cli`) |

---

### Backend Setup

Each microservice runs independently. Start them in this order:

```bash
git clone https://github.com/sheikhsaifali/match-simulator.git
cd match-simulator

cd TeamService && ./mvnw spring-boot:run && cd ..
cd players && ./mvnw spring-boot:run && cd ..
cd ScoringService && ./mvnw spring-boot:run && cd ..
cd simulation && ./mvnw spring-boot:run && cd ..
cd match_service && ./mvnw spring-boot:run
```

---

### Frontend Setup

```bash
cd frontend
npm install
ng serve
```

Open: http://localhost:4200

---

## 🗂️ Project Structure

```
match-simulator/
├── match_service/
├── ScoringService/
├── TeamService/
├── players/
├── simulation/
└── .idea/
```

---

## 🧠 How the Simulation Works

1. Fetch batsman attributes  
2. Evaluate bowler attributes  
3. Run probability model  
4. Update score via ScoringService  
5. Control flow via match_service  

---

## 🤝 Contributing

Fork → branch → commit → push → PR

---

## 📄 License

MIT License

---

## 👤 Author

Sheikh Saif Ali  
https://github.com/sheikhsaifali
