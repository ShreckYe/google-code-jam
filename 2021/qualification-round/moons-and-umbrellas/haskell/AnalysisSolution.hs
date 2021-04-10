import Control.Monad (forM_)
import Data.Vector (generate, (!))

main :: IO ()
main = do
  t <- readLn
  forM_ (take t [0 ..]) testCase

testCase :: Int -> IO ()
testCase ti = do
  line <- getLine
  let [xW, yW, s] = words line
      x = read xW
      y = read yW
      ans = f x y s
  putStrLn ("Case #" ++ show (ti + 1) ++ ": " ++ show ans)
  where
    f :: Int -> Int -> String -> Int
    f x y s = memoizedFFrom 0
      where
        memoizedFFrom :: Int -> Int
        memoizedFFrom = (generate (length s + 1) fFrom !)
          where
            fFrom :: Int -> Int
            fFrom = fWithExtraPrefixFrom ""
            fWithExtraPrefixFrom :: String -> Int -> Int
            fWithExtraPrefixFrom prefix from = case prefix ++ drop from s of
              '?' : _ -> min (recReplaceFirst 'C') (recReplaceFirst 'J')
              a : '?' : _ -> min (recReplaceSecond 'C') (recReplaceSecond 'J')
              a : a' : _ | a == a' -> recDropFirst
              'C' : 'J' : _ -> x + recDropFirst
              'J' : 'C' : _ -> y + recDropFirst
              [_] -> 0
              [] -> 0
              where
                recReplaceFirst c = case prefix of
                  _ : p -> fWithExtraPrefixFrom (c : p) from
                  [] -> fWithExtraPrefixFrom [c] (from + 1)
                recReplaceSecond c = case prefix of
                  a : _ : p -> fWithExtraPrefixFrom (a : c : p) from
                  [a] -> fWithExtraPrefixFrom [a, c] (from + 1)
                  [] -> fWithExtraPrefixFrom [s !! from, c] (from + 2)
                recDropFirst = case prefix of
                  _ : p -> fWithExtraPrefixFrom p from
                  [] -> fFrom (from + 1)