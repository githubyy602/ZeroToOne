const Base64 = {
    keyStr: 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=',
    encode(input) {
        let output = '';
        let chr1, chr2, chr3, enc1, enc2, enc3, enc4;
        let i = 0;
        input = Base64.utf8Encode(input);
        while (i < input.length) {
            chr1 = input.charCodeAt(i++);
            chr2 = input.charCodeAt(i++);
            chr3 = input.charCodeAt(i++);
            enc1 = chr1 >> 2;
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
            enc4 = chr3 & 63;
            if (isNaN(chr2)) {
                enc3 = enc4 = 64;
            } else if (isNaN(chr3)) {
                enc4 = 64;
            }
            output = output +
                Base64.keyStr.charAt(enc1) + Base64.keyStr.charAt(enc2) +
                Base64.keyStr.charAt(enc3) + Base64.keyStr.charAt(enc4);
        }
        return output;
    },
    decode(input) {
        let output = '';
        let chr1, chr2, chr3;
        let enc1, enc2, enc3, enc4;
        let i = 0;
        input = input.replace(/[^A-Za-z0-9+/=]/g, '');
        while (i < input.length) {
            enc1 = Base64.keyStr.indexOf(input.charAt(i++));
            enc2 = Base64.keyStr.indexOf(input.charAt(i++));
            enc3 = Base64.keyStr.indexOf(input.charAt(i++));
            enc4 = Base64.keyStr.indexOf(input.charAt(i++));
            chr1 = (enc1 << 2) | (enc2 >> 4);
            chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
            chr3 = ((enc3 & 3) << 6) | enc4;
            output = output + String.fromCharCode(chr1);
            if (enc3 !== 64) {
                output = output + String.fromCharCode(chr2);
            }
            if (enc4 !== 64) {
                output = output + String.fromCharCode(chr3);
            }
        }
        output = Base64.utf8Decode(output);
        return output;
    },
    utf8Encode(string) {
        string = string.replace(/\r\n/g, '\n');
        let utfString = '';
        for (let i = 0; i < string.length; i++) {
            let c = string.charCodeAt(i);
            if (c < 128) {
                utfString += String.fromCharCode(c);
            } else if ((c > 127) && (c < 2048)) {
                utfString += String.fromCharCode((c >> 6) | 192);
                utfString += String.fromCharCode((c & 63) | 128);
            } else {
                utfString += String.fromCharCode((c >> 12) | 224);
                utfString += String.fromCharCode(((c >> 6) & 63) | 128);
                utfString += String.fromCharCode((c & 63) | 128);
            }
        }
        return utfString;
    },
    utf8Decode(utfString) {
        let string = '';
        let i = 0;
        let c = 0;
        let c2 = 0;
        let c3 = 0;
        while (i < utfString.length) {
            c = utfString.charCodeAt(i);
            if (c < 128) {
                string += String.fromCharCode(c);
                i++;
            } else if ((c > 191) && (c < 224)) {
                c2 = utfString.charCodeAt(i + 1);
                string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
                i += 2;
            } else {
                c2 = utfString.charCodeAt(i + 1);
                c3 = utfString.charCodeAt(i + 2);
                string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
                i += 3;
            }
        }
        return string;
    }
};