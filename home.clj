(ns ret-g.routes.home
  (:require
   [ret-g.layout :as layout]
   [clojure.java.io :as io]
   [ret-g.middleware :as middleware]
   [ring.util.response]
   [ring.util.http-response :as response]
   [monger.core :as mongo]
    [monger.collection :as coll]))

(def bag (range 0 10) )

(def bag-2 (range 0 102))

(mongo/connect {:host "127.0.0.1" :port 27017})






(def level-1-played (atom (loop  [  ele (nth bag (int (Math/floor (rand 10) )) ) coll [] ]
                
                  ( if (= (count coll) 5 ) 
                          coll
                    (recur (nth bag (int (Math/floor (rand 10) )) ) (if ( (set coll) ele ) coll (conj coll  ele )  ))))) )

(def dist-coords (atom (let  [ orig (for [ x [ 0 200 400 600 800 ] y  [ 200 400 600 800 1000 ]] (vector x y ))
                          dist-cords (loop  [  ele (nth orig (int (Math/floor (rand 25) )) ) coll [] ]
                
                  ( if (= (count coll) 5 ) 
                          coll
                    (recur (nth orig (int (Math/floor (rand 25) )) ) (if ( (set coll) ele ) coll (conj coll  ele )  )))) ]
            
                     dist-cords ) ))

(def level-2-played (atom (loop  [  ele (nth bag-2 (int (Math/floor (rand 100) )) ) coll [] ]
                
                  ( if (= (count coll) 10 ) 
                          coll
                    (recur (nth bag-2 (int (Math/floor (rand 100) )) ) (if ( (set coll) ele ) coll (conj coll  ele )  ))))) )


(def dist-coords-2 (atom (let  [ orig (for [ x [ 0 200 400 600 800 ] y  [ 200 400 600 800 1000 ]] (vector x y ))
                          dist-cords (loop  [  ele (nth orig (int (Math/floor (rand 25) )) ) coll [] ]
                
                  ( if (= (count coll) 10 ) 
                          coll
                    (recur (nth orig (int (Math/floor (rand 25) )) ) (if ( (set coll) ele ) coll (conj coll  ele )  )))) ]
            
                     dist-cords ) ))


(def level-3-played (atom (loop  [  ele (nth bag-2 (int (Math/floor (rand 100) )) ) coll [] ]
                
                  ( if (= (count coll) 15 ) 
                          coll
                    (recur (nth bag-2 (int (Math/floor (rand 100) )) ) (if ( (set coll) ele ) coll (conj coll  ele )  ))))) )


(def dist-coords-3 (atom (let  [ orig (for [ x [ 0 200 400 600 800 ] y  [ 200 400 600 800 1000 ]] (vector x y ))
                          dist-cords (loop  [  ele (nth orig (int (Math/floor (rand 25) )) ) coll [] ]
                
                  ( if (= (count coll) 15 ) 
                          coll
                    (recur (nth orig (int (Math/floor (rand 25) )) ) (if ( (set coll) ele ) coll (conj coll  ele )  )))) ]
            
                     dist-cords ) ))





(def level-4-played (atom (loop  [  ele (nth bag-2 (int (Math/floor (rand 100) )) ) coll [] ]
                
                  ( if (= (count coll) 20 ) 
                          coll
                    (recur (nth bag-2 (int (Math/floor (rand 100) )) ) (if ( (set coll) ele ) coll (conj coll  ele )  ))))) )


(def dist-coords-4 (atom (let  [ orig (for [ x [ 0 200 400 600 800 ] y  [ 200 400 600 800 1000 ]] (vector x y ))
                          dist-cords (loop  [  ele (nth orig (int (Math/floor (rand 25) )) ) coll [] ]
                
                  ( if (= (count coll) 20 ) 
                          coll
                    (recur (nth orig (int (Math/floor (rand 25) )) ) (if ( (set coll) ele ) coll (conj coll  ele )  )))) ]
            
                     dist-cords ) ))







             




(defn home-page [request]
  (do       

         (reset!  level-1-played       (loop  [  ele (nth bag (int (Math/floor (rand 10) )) ) coll [] ]
                
                  ( if (= (count coll) 5 ) 
                          coll
                    (recur (nth bag (int (Math/floor (rand 10) )) ) (if ( (set coll) ele ) coll (conj coll  ele )  ))))             )


        (reset!   dist-coords  (let  [ orig (for [ x [ 0 200 400 600 800 ] y  [ 200 400 600 800 1000 ]] (vector x y ))
                          dist-cords (loop  [  ele (nth orig (int (Math/floor (rand 25) )) ) coll [] ]
                
                  ( if (= (count coll) 5 ) 
                          coll
                    (recur (nth orig (int (Math/floor (rand 25) )) ) (if ( (set coll) ele ) coll (conj coll  ele )  )))) ]
            
                     dist-cords )  )

        (layout/render request "home.html" {:gameEls   (cheshire.core/generate-string (for [x @level-1-played] (vector  x  )))
                                         :distC (cheshire.core/generate-string @dist-coords) 
                                       } )))
(defn  level-1 [ req ]

(layout/render req   "level-1.html"  {:played (cheshire.core/generate-string @level-1-played)  :required (cheshire.core/generate-string @dist-coords ) } ))

(defn lev-1-results [  req ]
   
           (let [ played  (reverse (cheshire.core/parse-string  ((req :query-params ) "played")) )
                  correct   (cheshire.core/parse-string ((req :query-params ) "correcto"))  
                  filtered ( filter   #(and   ( and  
                                         (>= ( (first % ) 0) ( (second % ) 0) )
          
                                         (<= ( (first % ) 0) ( + ( (second % ) 0)  200 ) ))
   
                                             ( and  
                                                      (<= ( (first % ) 1) ( (second % ) 1) )
          
                                            (>= ( (first % ) 1) ( - ( (second % ) 1)  200 ) )) )  (map vector   played correct  ) ) ]

    
           

 
                       (layout/render req "score.html" {:score ( * (/  (count filtered )  5 ) 100 ) })))


(defn     lev-2     [ request ] 
  
     (do       

         (reset!  level-2-played       (loop  [  ele (nth bag-2 (int (Math/floor (rand 100) )) ) coll [] ]
                
                  ( if (= (count coll) 10 ) 
                          coll
                    (recur (nth bag-2 (int (Math/floor (rand 100) )) ) (if ( (set coll) ele ) coll (conj coll  ele )  ))))             )


        (reset!   dist-coords-2  (let  [ orig (for [ x [ 0 200 400 600 800 ] y  [ 200 400 600 800 1000 ]] (vector x y ))
                          dist-cords (loop  [  ele (nth orig (int (Math/floor (rand 25) )) ) coll [] ]
                
                  ( if (= (count coll) 10 ) 
                          coll
                    (recur (nth orig (int (Math/floor (rand 25) )) ) (if ( (set coll) ele ) coll (conj coll  ele )  )))) ]
            
                     dist-cords )  )

        (layout/render request "level-2-home.html" {:gameEls   (cheshire.core/generate-string (for [x @level-2-played] (vector  x  )))
                                         :distC (cheshire.core/generate-string @dist-coords-2) 
                                       } )))
      
 



(defn    lev-2-player [ request ] 
     
     (layout/render "request" "level-2-play.html" {:played (cheshire.core/generate-string @level-2-played ) 
                 :required (cheshire.core/generate-string @dist-coords-2)  }))

 
 (defn   lev-2-results [ req ]
    
       (let [ played  (reverse (cheshire.core/parse-string  ((req :query-params ) "played")) )
                  correct   (cheshire.core/parse-string ((req :query-params ) "correcto"))  
                  filtered ( filter   #(and   ( and  
                                         (>= ( (first % ) 0) ( (second % ) 0) )
          
                                         (<= ( (first % ) 0) ( + ( (second % ) 0)  200 ) ))
   
                                             ( and  
                                                      (<= ( (first % ) 1) ( (second % ) 1) )
          
                                            (>= ( (first % ) 1) ( - ( (second % ) 1)  200 ) )) )  (map vector   played correct  ) ) ]

    
           

 
                       (layout/render req "score.html" {:score ( * (/  (count filtered )  10 ) 100 ) })))


     (defn  level-3-homer   [ request ]
 
          (do       

         (reset!  level-3-played       (loop  [  ele (nth bag-2 (int (Math/floor (rand 100) )) ) coll [] ]
                
                  ( if (= (count coll) 15 ) 
                          coll
                    (recur (nth bag-2 (int (Math/floor (rand 100) )) ) (if ( (set coll) ele ) coll (conj coll  ele )  ))))             )


        (reset!   dist-coords-3  (let  [ orig (for [ x [ 0 200 400 600 800 ] y  [ 200 400 600 800 1000 ]] (vector x y ))
                          dist-cords (loop  [  ele (nth orig (int (Math/floor (rand 25) )) ) coll [] ]
                
                  ( if (= (count coll) 15 ) 
                          coll
                    (recur (nth orig (int (Math/floor (rand 25) )) ) (if ( (set coll) ele ) coll (conj coll  ele )  )))) ]
            
                     dist-cords )  )

        (layout/render request "level-3-home.html" {:gameEls   (cheshire.core/generate-string (for [x @level-3-played] (vector  x  )))
                                         :distC (cheshire.core/generate-string @dist-coords-3) 
                                       } )))
      

(defn lev-3-player [ request ] 

    
     (layout/render "request" "level-3-play.html" {:played (cheshire.core/generate-string @level-3-played ) 
                 :required (cheshire.core/generate-string @dist-coords-3)  }))

 
(defn lev-3-results [ req ]


      (let [ played  (reverse (cheshire.core/parse-string  ((req :query-params ) "played")) )
                  correct   (cheshire.core/parse-string ((req :query-params ) "correcto"))  
                  filtered ( filter   #(and   ( and  
                                         (>= ( (first % ) 0) ( (second % ) 0) )
          
                                         (<= ( (first % ) 0) ( + ( (second % ) 0)  200 ) ))
   
                                             ( and  
                                                      (<= ( (first % ) 1) ( (second % ) 1) )
          
                                            (>= ( (first % ) 1) ( - ( (second % ) 1)  200 ) )) )  (map vector   played correct  ) ) ]

    
           

 
                       (layout/render req "score.html" {:score ( * (/  (count filtered )  15 ) 100 ) })))





     (defn  level-4-homer   [ request ]
 
          (do       

         (reset!  level-4-played       (loop  [  ele (nth bag-2 (int (Math/floor (rand 100) )) ) coll [] ]
                
                  ( if (= (count coll) 20 ) 
                          coll
                    (recur (nth bag-2 (int (Math/floor (rand 100) )) ) (if ( (set coll) ele ) coll (conj coll  ele )  ))))             )


        (reset!   dist-coords-4  (let  [ orig (for [ x [ 0 200 400 600 800 ] y  [ 200 400 600 800 1000 ]] (vector x y ))
                          dist-cords (loop  [  ele (nth orig (int (Math/floor (rand 25) )) ) coll [] ]
                
                  ( if (= (count coll) 20 ) 
                          coll
                    (recur (nth orig (int (Math/floor (rand 25) )) ) (if ( (set coll) ele ) coll (conj coll  ele )  )))) ]
            
                     dist-cords )  )

        (layout/render request "level-4-home.html" {:gameEls   (cheshire.core/generate-string (for [x @level-4-played] (vector  x  )))
                                         :distC (cheshire.core/generate-string @dist-coords-4) 
                                       } )))



(defn lev-4-player [ request ] 

    
     (layout/render "request" "level-4-play.html" {:played (cheshire.core/generate-string @level-4-played ) 
                 :required (cheshire.core/generate-string @dist-coords-4)  }))

 
(defn lev-4-results [ req ]


      (let [ played  (reverse (cheshire.core/parse-string  ((req :query-params ) "played")) )
                  correct   (cheshire.core/parse-string ((req :query-params ) "correcto"))  
                  filtered ( filter   #(and   ( and  
                                         (>= ( (first % ) 0) ( (second % ) 0) )
          
                                         (<= ( (first % ) 0) ( + ( (second % ) 0)  200 ) ))
   
                                             ( and  
                                                      (<= ( (first % ) 1) ( (second % ) 1) )
          
                                            (>= ( (first % ) 1) ( - ( (second % ) 1)  200 ) )) )  (map vector   played correct  ) ) ]

    
           

 
                       (layout/render req "score.html" {:score ( * (/  (count filtered )  20 ) 100 ) })))
 

 


(defn home-routes []
  [""
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/" {:get home-page}]
    ["/play-lev-1" {:get level-1}]
    ["/results"    {:get  lev-1-results }]
    ["/lev-2-home" {:get  lev-2 }]
     ["/play-lev-2" {:get  lev-2-player }]
     ["/results-2"   {:get  lev-2-results}]
      ["/lev-3-home"  {:get level-3-homer }]
      ["/play-lev-3"   {:get lev-3-player}]
      ["/results-3"    {:get lev-3-results}]
      ["/lev-4-home"  {:get level-4-homer }]
      ["/play-lev-4"   {:get lev-4-player}]
      ["/results-4"    {:get lev-4-results}]])

