// cryptoService.js

// 1. Generar o importar una llave (AES-GCM es el estándar moderno)
// const ENCRYPTION_KEY_RAW = import.meta.env.VITE_ENCRYPTION_KEY; // Debe tener 32 caracteres para 256 bits
const ENCRYPTION_KEY_RAW = "cryptographic-key-information-v1";

// Convertimos el string del .env en una CryptoKey válida
const getCryptoKey = async () => {
  const encoder = new TextEncoder();
  const keyData = encoder.encode(ENCRYPTION_KEY_RAW);
  return await window.crypto.subtle.importKey(
    "raw",
    keyData,
    { name: "AES-GCM" },
    false,
    ["encrypt", "decrypt"],
  );
};

export const encryptData = async (data) => {
  const key = await getCryptoKey();
  const iv = window.crypto.getRandomValues(new Uint8Array(12)); // Vector de inicialización (Sal)
  const encoder = new TextEncoder();

  const encrypted = await window.crypto.subtle.encrypt(
    { name: "AES-GCM", iv: iv },
    key,
    encoder.encode(JSON.stringify(data)),
  );

  // Unimos el IV y los datos cifrados para poder descifrarlos luego
  const combined = new Uint8Array(iv.byteLength + encrypted.byteLength);
  combined.set(iv);
  combined.set(new Uint8Array(encrypted), iv.byteLength);

  // Convertimos a Base64 para guardarlo en LocalStorage
  return btoa(String.fromCharCode(...combined));
};

export const decryptData = async (encryptedBase64) => {
  try {
    const key = await getCryptoKey();
    const combined = new Uint8Array(
      atob(encryptedBase64)
        .split("")
        .map((c) => c.charCodeAt(0)),
    );

    const iv = combined.slice(0, 12);
    const data = combined.slice(12);

    const decrypted = await window.crypto.subtle.decrypt(
      { name: "AES-GCM", iv: iv },
      key,
      data,
    );

    const decoder = new TextDecoder();
    return JSON.parse(decoder.decode(decrypted));
  } catch (error) {
    console.error("Error descifrando datos:", error);
    return null;
  }
};
