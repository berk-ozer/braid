(ns braid.lib.crypto
  (:require
   [clojure.string :as string])
  (:import
   (java.security SecureRandom MessageDigest)
   (javax.crypto Mac)
   (javax.crypto.spec SecretKeySpec)
   (org.apache.commons.codec.binary Base64)))

(defn random-nonce
  "url-safe random nonce"
  [size]
  (let [rand-bytes (let [seed (byte-array size)]
                     (.nextBytes (SecureRandom. ) seed)
                     seed)]
    (-> rand-bytes
        Base64/encodeBase64
        String.
        (string/replace "+" "-")
        (string/replace "/" "_")
        (string/replace "=" ""))))

(defn base64-encode
  [input]
  (-> input
      (.getBytes "UTF-8")
      Base64/encodeBase64
      String.))

(defn b64-sha1-encode
  "Get the base64-encode HMAC-SHA1 of `to-sign` with `key`"
  [to-sign key]
  (let [mac (Mac/getInstance "HmacSHA1")
        secret-key (SecretKeySpec. (.getBytes key "UTF-8") (.getAlgorithm mac))]
    (-> (doto mac (.init secret-key))
        (.doFinal (.getBytes to-sign "UTF-8"))
        Base64/encodeBase64
        String.)))

(defn str->bytes ^bytes
  [s]
  (.getBytes s "UTF-8"))

(defn hmac-sha256
  ^bytes [^bytes key-bytes ^bytes to-sign-bytes]
  (let [mac (Mac/getInstance "HmacSHA256")
        secret-key (SecretKeySpec. key-bytes (.getAlgorithm mac))]
    (-> (doto mac (.init secret-key))
        (.doFinal to-sign-bytes))))

(defn hmac-bytes
  [hmac-key data-bytes]
  (->> (hmac-sha256 (str->bytes hmac-key) data-bytes)
      (map (partial format "%02x"))
      (apply str)))

(defn hmac
  [hmac-key data]
  (hmac-bytes hmac-key (.getBytes data "UTF-8")))

(defn constant-comp
  "Compare two strings in constant time"
  [a b]
  (loop [a a b b match (= (count a) (count b))]
    (if (and (empty? a) (empty? b))
      match
      (recur
        (rest a)
        (rest b)
        (and match (= (first a) (first b)))))))

(defn hmac-verify
  [{:keys [secret mac data]}]
  (constant-comp mac (hmac secret data)))

(defn bytes->hex
  [^bytes bs]
  (->> bs (map (partial format "%02x")) (apply str)))

(defn sha256
  ^bytes [^bytes input-bytes]
  (-> (doto (MessageDigest/getInstance "SHA-256")
        (.update input-bytes))
      (.digest)))

(def hex-hash (comp bytes->hex sha256 str->bytes))

(defn md5
  ^bytes [^String s]
  (-> (doto (MessageDigest/getInstance "MD5")
        (.reset))
      (.digest (.getBytes s "UTF-8"))))
