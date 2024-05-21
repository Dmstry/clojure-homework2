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


;; Problem 69, Merge with a Function
;; Difficulty: medium
;; Write a function which takes a function f and a variable number of maps. Your function should return a map that consist of the rest of the maps conj-ed onto the first. If a key occurs in more than one map, the mapping(s) from the latter (left-to-right) should be combined with the mapping in the result by calling (f val-in-result val-in-latter).

(defn my-merge-with
  [f & maps]
  (reduce (fn [acc map]
            (reduce (fn [acc [k v]]
                      (if (contains? acc k)
                        (assoc acc k (f (acc k) v))
                        (assoc acc k v)))
                    acc
                    map))
          {}
          maps))


(my-merge-with * {:a 2, :b 3, :c 4} {:a 2} {:b 2} {:c 5}) ;{:a 4, :b 6, :c 20}

(my-merge-with - {1 10, 2 20} {1 3, 2 10, 3 15}) ;{1 7, 2 10, 3 15}

(my-merge-with concat {:a [3], :b [6]} {:a [4 5], :c [8 9]} {:b [7]}) ;{:a [3 4 5], :b [6 7], :c [8 9]}


;; Problem 59, Juxtaposition
;; Difficulty: medium
;; Take a set of functions and return a new function that takes a variable number of arguments and returns a sequence containing the result of applying each function left-to-right to the arguments.

(defn my-juxt
  [& funcs]
  (fn [& args]
    (map #(apply % args) funcs)))

((my-juxt + max min) 2 3 5 1 6 4) ;(3 6 1)

((my-juxt
  #(.toUpperCase %)
  count) "hello") ;("HELLO" 5)

((my-juxt :a :b :c) {:a 2, :b 4, :c 6, :d 8 :e 10}) ;(2 4 6)