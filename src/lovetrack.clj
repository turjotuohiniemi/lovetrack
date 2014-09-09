(ns lovetrack)

(def pair_letters [\p \a \i \r \s])
(def names_filename "/Users/turjo/clojutus/lovetrack/fast_track_generoitu_nimilista.txt")
(def required_percentage 99)

(defn loveadd [a b]
  (if (< (+ a b) 10)
    (+ a b)
    (let [numval (fn [x] (- (int x) (int \0)))]
      (loveadd (reduce (fn [a b] (+ a b)) (map numval (vec (str (+ a b))))) 0))))

(defn pairvector [namestr]
  (let [freqs (frequencies namestr)
        occurs_count (fn [letter]
                           (if (nil? (freqs letter))
                             0
                             (loveadd (freqs letter) 0)))]
    (map occurs_count pair_letters)))

(defn pairsum [pairvect]
  (let [calc_sums (loop [acc []
                         v pairvect]
                    (if (second v)
                      (recur (conj acc (loveadd (first v) (second v))) (rest v))
                      acc))]
    (if (= (count pairvect) 2)
      (+ (* (first pairvect) 10) (last pairvect))
      (pairsum calc_sums)
      )))

(defn percentage [name1 name2]
  (pairsum (pairvector (str name1 name2))))

(defn good_pairs [filename]
  (let [pairs_for (fn [dev respool]
                    (map (fn [match] (str dev " - " match)) (filter (fn [codev] (>= (percentage dev codev) required_percentage)) respool)))]
    (loop [acc '()
           developers (clojure.string/split-lines (.toLowerCase (slurp filename)))]
      (if (empty? developers)
        acc
        (recur (concat acc (pairs_for (first developers) (rest developers))) (rest developers)))) ))
