(ns snippets.output
  (:require #?@(:cljs [quil.snippet]
                :clj [[quil.snippet :refer [defsnippet]]
                      [clojure.java.io :as io]
                      [clojure.test :refer [is]]])
            [quil.core :as q :include-macros true])
  #?(:cljs
     (:use-macros [quil.snippet :only [defsnippet]])))

#?(:clj
   (defsnippet begin-raw-end-raw {:renderer :p3d}
     (q/begin-raw :dxf "generated/dxf.txt")
     (q/camera 150 150 150 0 0 0 0 0 1)
     (q/box 100)
     (q/end-raw)))

#?(:clj
   (defsnippet save {:renderer :p3d}
     (q/camera 150 150 150 0 0 0 0 0 1)
     (q/box 100)
     (q/save "generated/box.png")))

#?(:clj
   (defsnippet save-frame {:renderer :p3d}
     (q/camera 150 150 150 0 0 0 0 0 1)
     (q/background 127)
     (q/with-rotation [(/ (q/frame-count) 10)]
       (q/box 100))
     (q/save-frame)
     (q/save-frame "generated/rotating-box-####.png")))

#?(:clj
   (defsnippet do-record {}
     (doseq [type [:svg :pdf]
             i (range 3)]
       ; render 3 pdf files and check that each is non-empty
       ; at the end
       (let [file (str "generated/record_" i "." (name type))]
        (q/do-record (q/create-graphics 200 200 type file)
                     (q/fill 255 0 0)
                     (q/ellipse 100 100
                                (+ 50 (* 50 i))
                                (+ 50 (* 50 i))))
        (is (pos? (.length (io/file file))))))))

