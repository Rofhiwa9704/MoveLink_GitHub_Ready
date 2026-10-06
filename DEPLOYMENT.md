# MoveLink deployment

## Local development

Backend and PostgreSQL can be started with Docker from the project root:

```bash
docker compose up --build
```

The web application is then available at `http://localhost`.

For frontend-only development, run `npm install` and `npm run dev` inside `frontend`. Vite can use `/api` against the backend through the configured environment/proxy setup.

## First customer

Open `/register`, create an account, and the application signs the customer in automatically. The account is stored in PostgreSQL.

## Optional first admin

Set `MOVELINK_ADMIN_EMAIL` and `MOVELINK_ADMIN_PASSWORD` in `.env` before starting the backend. The application will create the admin only when that email does not already exist.

## Production

1. Copy `.env.example` to `.env`.
2. Set a strong PostgreSQL password.
3. Set `APP_CORS_ORIGINS` to the real web/mobile API origin.
4. Set SMTP credentials only if email features are enabled.
5. Set admin credentials if an initial admin is required.
6. Run `docker compose up -d --build`.
7. Put HTTPS/TLS in front of the application using your hosting provider or reverse proxy.

The frontend proxies `/api` to the backend, so the browser does not need a hard-coded localhost backend URL in production.
