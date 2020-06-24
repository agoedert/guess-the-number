(ns guess-the-number.core
  (:gen-class))

(defn get-random-int-between-range [min-value max-value]
  (+ (rand-int (+ (- max-value min-value) 1)) min-value))

(defn create-number-guesser [min-value max-value]
  (let [number (get-random-int-between-range min-value max-value)]
    (fn [guess] (if (= guess number) "Match" (if (> guess number) "Too high" "Too low")))))

(defn process-user-input [guesser]
    (let [guess (guesser (Integer/parseInt (read-line)))]
      (if (= guess "Match")
        (do (println "You guessed the number, congratulations!") true)
        (do (println (str guess ", try again....")) (process-user-input guesser) false))))

(defn -main []
  (let [minValue 1
        maxValue 100
        guesser (create-number-guesser minValue maxValue)]
    (println "Choose a value between" minValue "and" maxValue)
    (process-user-input guesser)))
