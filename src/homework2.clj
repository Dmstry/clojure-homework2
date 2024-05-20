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


;; Problem 58, Function composition
;; Difficulty: medium
;; Write a function which allows you to create function compositions. The parameters list should take a variable number of functions, and create a function applies them from right-to-left.

(defn my-comp
  [& funcs]
  (fn [& args]
    (reduce (fn [acc f]
              (f acc))
            (apply (last funcs) args)
            (reverse (butlast funcs)))))

((my-comp rest reverse) [1 2 3 4]) ;[3 2 1]

((my-comp (partial + 3) second) [1 2 3 4]) ;5

((my-comp zero? #(mod % 8) +) 3 5 7 9) ;true

((my-comp #(.toUpperCase %) #(apply str %) take) 5 "hello world") ;"HELLO"