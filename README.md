# PR09 - Apps Script API

Aplicació Android desenvolupada amb Jetpack Compose que consumeix una API creada amb Google Apps Script sobre un Google Sheets amb dades de tracks de Spotify.
Projecte realitzat per a l’assignatura 0488: Desenvolupament d’interfícies (DAM2A).
Desenvolupat per Lluis i Andreu.

## Dataset

- Dataset base: `high_popularity_spotify_data.csv`
- Tematica: tracks de Spotify
- Font de dades: Kaggle

## Funcionalitats

- Registre i login local amb SharedPrefs
- Llistat de tracks carregats des de Google Sheets amb peticions GET
- Formulari per afegir nous tracks amb peticions POST
- Arquitectura MVVM
- Retrofit per consumir la API
- LiveData per gestionar l'estat

## Endpoints de la API

- `GET action=list`
- `GET action=search&text=...`
- `GET action=detail&id=...`
- `POST action=add`

El codi de l'Apps Script es troba a [apps-script/Code.gs](./apps-script/Code.gs).

## Variables d'entorn

Cal crear un fitxer `secrets.properties` a la arrel del projecte amb:

```properties
BASE_URL=URL_DEL_DEPLOY_DE_APPS_SCRIPT
API_KEY=spotify123
```

## Estructura principal

- `app/src/main/java/com/example/api/data`
- `app/src/main/java/com/example/api/ui`
- `app/src/main/java/com/example/api/util`
- `apps-script/Code.gs`

## Captures

- Pantalla de registre
  
  <img width="327" height="673" alt="image" src="https://github.com/user-attachments/assets/0f067031-d063-40d3-b3d3-2c8ef4993009" />

- Pantalla de login
  
  <img width="325" height="671" alt="image" src="https://github.com/user-attachments/assets/e28986df-15e8-4fbb-ac9e-ade08d5a8044" />

- Pantalla de llistat
  
  <img width="323" height="672" alt="image" src="https://github.com/user-attachments/assets/dd5cdbf7-12bc-4162-8de8-39341946c282" />

- Pantalla de formulari POST
  
  <img width="325" height="672" alt="image" src="https://github.com/user-attachments/assets/fa1f2037-996b-4b1d-963a-5f179cfd3f51" />

- Exemple de dades noves afegides al Google Sheets
  
  <img width="9887" height="512" alt="image" src="https://github.com/user-attachments/assets/c83c854e-a982-48c0-9c78-7f7371802d4f" />


## Video demo

https://youtu.be/nBp1q518s6c
