(ns lovetrack-test
  (:use midje.sweet
        lovetrack))

(def matti "Matti Meikäläinen")
(def erkki "Erkki Esimerkki")
(def tiina "Tiina Testaaja")

(facts "name->pairvector"
       (name->pairvector matti) => '(0 1 3 0 0)
       (name->pairvector "jopi manneri - tomas hirviniemi") => '(1 2 6 2 1)
       )

(facts "loveadd"
       (loveadd 1 2) => 3
       (loveadd 1234567 1234567) => 2
       )

(def esim (name->pairvector (str matti erkki)))

(facts "name->pairvector"
       (pairvector->lovevalue esim) => 58
       (pairvector->lovevalue [1 2]) => 12
       )

(facts "pairvector->lovevalue"
       (pairvector->lovevalue (name->pairvector (str erkki tiina))) => 29
       )

(facts "percentage"
       (percentage erkki tiina) => 29
       )



