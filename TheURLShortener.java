// This is a simple URL Shortener Implementation that I created in JavaScript. Zachary Wile.

// The line below is how I define the class that the file belongs to.
class URLShortener {
  
  // This is a constructor, which you create to initialize objects of a class.
  constructor() {
    this.urlMapping = new Map();           // This is how I map short IDs to original URLs. 'this.' is how you refer to the current object.
    this.shortURL = "https://base.ly/";  // This is the base URL I'm using for all of the shortened links. The .ly is a way to say that there is more to follow.
  }

  // This is a method I use for random short ID for URL shortening 
  generateShortID(length = 6) { const chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; // By writing all of this out I am creating characters that you can initialize in short IDs.
    let shortID = "";                   // This is for Initializing an empty string to store a generated short ID
    
    // This is how I generate a random short ID of a specified length
    for (let i = 0; i < length; i++) {
      // This is how I append a random character from 'chars' to 'shortID'
      shortID += chars.charAt(Math.floor(Math.random() * chars.length));
    }
    return shortID;                     // This returns your generated short ID for my URL shortener.
  }

  // Method to shorten a given original URL
  shortenURL(originalURL) {
    // Check if the URL has already been shortened previously
    for (const [key, value] of this.urlMapping.entries()) {
      if (value === originalURL) {
        // Return existing short URL if original URL is already shortened
        return this.baseURL + key;
      }
    }

    let shortID;                         // Declare a variable to hold the short ID
    // Generate a unique short ID that doesn't already exist in the urlMap
    do {
      shortID = this.generateShortID();  // Generate a new short ID
    } while (this.urlMap.has(shortID));  // Repeat if shortID already exists

    // Add the new short ID and corresponding original URL to the map
    this.urlMap.set(shortID, originalURL);

    // Return the complete shortened URL
    return this.baseURL + shortID;
  }

  // Method to retrieve the original URL given a shortened URL
  getOriginalURL(shortURL) {
    // Extract the short ID portion from the provided short URL
    const shortID = shortURL.replace(this.baseURL, '');

    // Retrieve and return the original URL from the map; return null if not found
    return this.urlMap.get(shortID) || null;
  }

  // Optional method to list all shortened URL mappings currently stored
  listURLs() {
    // Convert all map entries into an array of objects with short and original URLs
    return Array.from(this.urlMap.entries()).map(([short, original]) => ({
      shortURL: this.baseURL + short, // Construct the full short URL
      originalURL: original           // Original URL stored in the mapping
    }));
  }
}

// Belows my example of the usage.

// Create an instance of URLShortener class
const shortener = new URLShortener();

// Shorten the URL "https://www.google.com" and store the short URL
const googleShort = shortener.shortenURL("https://www.google.com");

// Shorten the URL "https://github.com" and store the short URL
const githubShort = shortener.shortenURL("https://github.com");

// Output the shortened URL for Google
console.log("Short URL for Google:", googleShort);

// Output the shortened URL for GitHub
console.log("Short URL for GitHub:", githubShort);

// This outputs and retrieves an original URL from Google's short URL
console.log("Original URL from Google short link:", shortener.getOriginalURL(googleShort));

// This is my output for the full list of all the different URL mappings (original and shortened URLs)
console.log("All shortened URLs:", shortener.listURLs());
