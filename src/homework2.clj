(ns homework2)

;; Problem 158, Decurry
;; Difficulty: medium
;; Write a function that accepts a curried function of unknown arity n. Return an equivalent function of n arguments.

(defn decurry [curried-fn]
  (fn [& args]
    (reduce (fn [f arg] (f arg)) curried-fn args)))


((decurry (fn [a]
            (fn [b]
              (fn [c]
                (fn [d]
                  (+ a b c d)))))) 1 2 3 4) ;10

((decurry (fn [a]
            (fn [b]
              (fn [c]
                (fn [d]
                  (* a b c d)))))) 1 2 3 4) ;24

((decurry (fn [a]
            (fn [b]
              (* a b)))) 5 5) ;25