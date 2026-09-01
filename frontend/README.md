# MoveLink Frontend

Production-ready React/Vite frontend for the existing MoveLink Spring Boot API.

## Local development

1. Start PostgreSQL and the existing Spring Boot backend on port 8080.
2. Copy `.env.example` to `.env` if you need a different API URL.
3. Run:

```bash
npm install
npm run dev
```

The web app runs at `http://localhost:5173`.

## Production build

Set `VITE_API_URL` to the deployed backend API URL, then:

```bash
npm run build
npm run preview
```

## Android / iPhone

The UI is responsive and the project includes `capacitor.config.js` so the same frontend can be packaged as a native app without changing the Spring Boot backend.

After installing the Capacitor packages in this frontend:

```bash
npm install @capacitor/core @capacitor/cli @capacitor/android @capacitor/ios
npx cap add android
npx cap add ios
npm run build
npx cap sync
npx cap open android
npx cap open ios
```

### Important backend deployment note

The existing backend currently allows browser CORS from `http://localhost:5173`. I have intentionally **not changed the backend**, as requested. For a deployed web frontend, the backend will need to serve the frontend from the same origin or its existing CORS policy will need to be extended to the production frontend domain. The frontend itself is configured to use `VITE_API_URL`, so the API URL can be changed without editing the React source.
