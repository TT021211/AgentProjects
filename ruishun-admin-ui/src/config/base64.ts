// base64.ts
export const Base64 = {
  keyStr: 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=',

  encode(input: string): string {
    let output = '';
    let chr1: number;
    let chr2: number;
    let chr3: number;
    let enc1: number;
    let enc2: number;
    let enc3: number;
    let enc4: number;
    let i = 0;

    const utf8Input = this.utf8Encode(input);

    while (i < utf8Input.length) {
      chr1 = utf8Input.charCodeAt(i);
      chr2 = utf8Input.charCodeAt(i + 1);
      chr3 = utf8Input.charCodeAt(i + 2);

      enc1 = chr1 >> 2;
      enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
      enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
      enc4 = chr3 & 63;

      if (isNaN(chr2)) {
        enc3 = enc4 = 64;
      } else if (isNaN(chr3)) {
        enc4 = 64;
      }

      output =
        output +
        this.keyStr.charAt(enc1) +
        this.keyStr.charAt(enc2) +
        this.keyStr.charAt(enc3) +
        this.keyStr.charAt(enc4);
      i += 3;
    }

    return output;
  },

  decode(input: string): string {
    let output = '';
    let chr1: number;
    let chr2: number;
    let chr3: number;
    let enc1: number;
    let enc2: number;
    let enc3: number;
    let enc4: number;
    let i = 0;

    const base64Input = input.replace(/[^A-Za-z0-9+/=]/g, '');

    while (i < base64Input.length) {
      enc1 = this.keyStr.indexOf(base64Input.charAt(i));
      enc2 = this.keyStr.indexOf(base64Input.charAt(i + 1));
      enc3 = this.keyStr.indexOf(base64Input.charAt(i + 2));
      enc4 = this.keyStr.indexOf(base64Input.charAt(i + 3));

      chr1 = (enc1 << 2) | (enc2 >> 4);
      chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
      chr3 = ((enc3 & 3) << 6) | enc4;

      output += String.fromCharCode(chr1);

      if (enc3 !== 64) {
        output += String.fromCharCode(chr2);
      }
      if (enc4 !== 64) {
        output += String.fromCharCode(chr3);
      }

      i += 4;
    }

    return this.utf8Decode(output);
  },

  utf8Encode(string: string): string {
    let utftext = '';
    let c: number;

    for (let n = 0; n < string.length; n += 1) {
      c = string.charCodeAt(n);

      if (c < 128) {
        utftext += String.fromCharCode(c);
      } else if (c < 2048) {
        utftext += String.fromCharCode((c >> 6) | 192);
        utftext += String.fromCharCode((c & 63) | 128);
      } else {
        utftext += String.fromCharCode((c >> 12) | 224);
        utftext += String.fromCharCode(((c >> 6) & 63) | 128);
        utftext += String.fromCharCode((c & 63) | 128);
      }
    }

    return utftext;
  },

  utf8Decode(utftext: string): string {
    let string = '';
    let i = 0;
    let c = 0;
    let c1 = 0;
    let c2 = 0;
    // const c3 = 0;

    while (i < utftext.length) {
      c = utftext.charCodeAt(i);

      if (c < 128) {
        string += String.fromCharCode(c);
        i += 1;
      } else if (c < 224) {
        c1 = utftext.charCodeAt(i + 1);
        string += String.fromCharCode(((c & 31) << 6) | (c1 & 63));
        i += 2;
      } else {
        c1 = utftext.charCodeAt(i + 1);
        c2 = utftext.charCodeAt(i + 2);
        string += String.fromCharCode(((c & 15) << 12) | ((c1 & 63) << 6) | (c2 & 63));
        i += 3;
      }
    }

    return string;
  }
};
