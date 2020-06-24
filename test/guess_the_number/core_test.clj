(ns guess-the-number.core-test
  (:require [clojure.test :refer :all]
            [guess-the-number.core :refer :all]))

(deftest core-test

  (def guesser (create-number-guesser 50 50))
  
  (defn make-input [input]
    (apply str (interleave input (repeat "\n"))))

  (defn assert-console-output [expected-value & input]
    (is (= expected-value 
        (with-in-str (make-input input)
          (with-out-str (process-user-input guesser))))))

  (testing "Random number generation between the given range"
    (let [number (get-random-int-between-range 1 100)]
      (is (>= number 1))
      (is (<= number 100))))

  (testing "Feedback about the guess"
      (is (= "Match" (guesser 50)))
      (is (= "Too low" (guesser 49)))
      (is (= "Too high" (guesser 51))))
      
  (testing "Output for correct user guess"
    (assert-console-output "You guessed the number, congratulations!\n" "50"))
          
  (testing "Output for user guess too low"
    (assert-console-output "Too low, try again....\nYou guessed the number, congratulations!\n" "49" "50"))
          
  (testing "Output for user guess too high"
    (assert-console-output "Too high, try again....\nYou guessed the number, congratulations!\n" "51" "50"))

  (testing "Output for valid range indication"
    (with-redefs [create-number-guesser (constantly guesser)]
      (is (= "Choose a value between 1 and 100\nYou guessed the number, congratulations!\n"
        (with-in-str (make-input '("50"))
          (with-out-str (-main))))))))
