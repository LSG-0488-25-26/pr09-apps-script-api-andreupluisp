const SHEET_NAME = 'high_popularity_spotify_data.csv';

function doGet(e) {
  try {
    const apiKey = e && e.parameter ? e.parameter.apiKey || '' : '';
    if (!isValidApiKey_(apiKey)) {
      return jsonResponse_({ ok: false, error: 'API_KEY incorrecta' });
    }

    const action = e.parameter.action || 'list';

    if (action === 'list') {
      return listTracks_(e);
    }

    if (action === 'search') {
      return searchTracks_(e);
    }

    if (action === 'detail') {
      return detailTrack_(e);
    }

    return jsonResponse_({ ok: false, error: 'action no valida' });
  } catch (error) {
    return jsonResponse_({ ok: false, error: error.message });
  }
}

function doPost(e) {
  try {
    const body = e && e.postData && e.postData.contents
      ? JSON.parse(e.postData.contents)
      : {};

    if (!isValidApiKey_(body.apiKey || '')) {
      return jsonResponse_({ ok: false, error: 'API_KEY incorrecta' });
    }

    if ((body.action || '') !== 'add') {
      return jsonResponse_({ ok: false, error: 'action no valida' });
    }

    if (!body.track_name || !body.track_artist) {
      return jsonResponse_({
        ok: false,
        error: 'track_name y track_artist son obligatorios'
      });
    }

    const sheet = getSheet_();
    const headers = getHeaders_(sheet);

    const rowData = {
      track_name: body.track_name || '',
      track_artist: body.track_artist || '',
      playlist_genre: body.playlist_genre || '',
      track_album_release_date: body.track_album_release_date || '',
      track_album_name: body.track_album_name || '',
      track_id: body.track_id || Utilities.getUuid(),
      track_popularity: body.track_popularity || '',
      uri: body.uri || ''
    };

    const row = headers.map((header) => rowData[header] || '');
    sheet.appendRow(row);

    return jsonResponse_({
      ok: true,
      message: 'Cancion anadida',
      item: rowData
    });
  } catch (error) {
    return jsonResponse_({ ok: false, error: error.message });
  }
}

function listTracks_(e) {
  const items = getAllTracks_();
  const limit = Number(e.parameter.limit || 0);

  return jsonResponse_({
    ok: true,
    total: items.length,
    items: limit > 0 ? items.slice(0, limit) : items
  });
}

function searchTracks_(e) {
  const text = (e.parameter.text || '').toLowerCase().trim();
  const limit = Number(e.parameter.limit || 0);

  if (!text) {
    return jsonResponse_({ ok: false, error: 'falta text' });
  }

  const items = getAllTracks_().filter((item) => {
    const name = String(item.track_name || '').toLowerCase();
    const artist = String(item.track_artist || '').toLowerCase();
    return name.includes(text) || artist.includes(text);
  });

  return jsonResponse_({
    ok: true,
    total: items.length,
    items: limit > 0 ? items.slice(0, limit) : items
  });
}

function detailTrack_(e) {
  const id = e.parameter.id || '';
  const uri = e.parameter.uri || '';

  if (!id && !uri) {
    return jsonResponse_({ ok: false, error: 'falta id o uri' });
  }

  const item = getAllTracks_().find((track) => {
    return String(track.track_id || '') === String(id) ||
      String(track.uri || '') === String(uri);
  });

  if (!item) {
    return jsonResponse_({ ok: false, error: 'track no encontrado' });
  }

  return jsonResponse_({ ok: true, item: item });
}

function getAllTracks_() {
  const sheet = getSheet_();
  const values = sheet.getDataRange().getValues();

  if (values.length < 2) {
    return [];
  }

  const headers = values[0];
  const rows = values.slice(1);

  return rows
    .filter((row) => row.some((cell) => cell !== ''))
    .map((row) => {
      const item = {};
      headers.forEach((header, index) => {
        item[header] = row[index];
      });
      return item;
    });
}

function getSheet_() {
  const sheet = SpreadsheetApp.getActiveSpreadsheet().getSheetByName(SHEET_NAME);

  if (!sheet) {
    throw new Error('No existe la pestana ' + SHEET_NAME);
  }

  return sheet;
}

function getHeaders_(sheet) {
  return sheet
    .getRange(1, 1, 1, sheet.getLastColumn())
    .getValues()[0];
}

function isValidApiKey_(apiKey) {
  const savedApiKey = PropertiesService.getScriptProperties().getProperty('API_KEY');
  return apiKey && savedApiKey && apiKey === savedApiKey;
}

function jsonResponse_(data) {
  return ContentService
    .createTextOutput(JSON.stringify(data))
    .setMimeType(ContentService.MimeType.JSON);
}
