(ns lovetrack)

(def love-letters [\p \a \i \r \s])
(def required_percentage 99)

(defn loveadd [a b]
  (if (< (+ a b) 10)
    (+ a b)
    (->> a
         (+ b)
         str
         vec
         (map #(- (int %) (int \0)))
         (reduce #(+ %1 %2))
         (loveadd 0))))

(defn name->pairvector [namestr]
  (let [freqs (frequencies namestr)
        occurs_count (fn [letter]
                           (if (nil? (freqs letter))
                             0
                             (loveadd (freqs letter) 0)))]
    (map occurs_count love-letters)))

(defn pairvector->lovevalue [pairvect]
  (let [calc_sums (loop [v pairvect
                         acc []]
                    (if (second v)
                      (->> (first v)
                           (loveadd (second v))
                           (conj acc)
                           (recur (rest v)))
                      acc))]
    (if (= (count pairvect) 2)
      (+ (* (first pairvect) 10) (last pairvect))
      (pairvector->lovevalue calc_sums)
      )))

(defn percentage [name1 name2]
  (-> (str name1 name2)
      name->pairvector
      pairvector->lovevalue))

(defn good-pairs [filename]
  (let [pairs-for (fn [dev respool]
                    (->> respool
                         (filter #(= (percentage dev %) required_percentage))
                         (map #(str dev " - " %))
                         ))]
    (loop [developers (clojure.string/split-lines (.toLowerCase (slurp filename)))
           acc '()]
      (if (empty? developers)
        acc
        (->> (rest developers)
             (pairs-for (first developers))
             (concat acc)
             (recur (rest developers)))))))
