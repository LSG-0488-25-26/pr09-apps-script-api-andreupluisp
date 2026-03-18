# PR09 - Apps Script API

App Android amb Jetpack Compose que consumeix una API feta amb Google Apps Script sobre un Google Sheets amb dades de tracks de Spotify.

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

Pendent d'afegir:

- Pantalla de registre
- Pantalla de login
- Pantalla de llistat
- Pantalla de formulari POST
- Exemple de dades noves afegides al Google Sheets

## Video demo

Pendent d'afegir l'enllac o el fitxer de la demo on es vegi:

- La app Android
- El Google Sheets
- El flux de dades de GET i POST

## Notes finals

- El Google Sheets s'ha de compartir amb `raimon.izard@itb.cat`
- Si es modifica l'Apps Script, cal tornar a desplegar la web app
