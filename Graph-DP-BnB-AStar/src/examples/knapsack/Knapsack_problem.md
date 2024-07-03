# Knapsack problem

The Knapsack problem starts from a list of objects L of size n. Each object $ob_i$ in the list is of the form $ob_i= (w_i, v_i, m_i)$ where $w_i, v_i, m_i$ are, respectively, its weight, its unit value and the number of units available. The knapsack has capacity $C$. The problem seeks to place in the knapsack the maximum number of units of each object, taking into account the available ones, that fit in the knapsack so that the value of these is maximum. 

## Data

- $w_i$: unit weight of the object $i$
- $v_i$: unit value of the object $i$
- $m_i$: number of available units of the object $i$
- $C$: knapsack capacity
- $n$: number of objects

## Model

If $x_i$ is the number of units of object $i$ in the knapsack the problem can be modeled as:

$$ \max \sum\limits_{i=0}^{n-1} x_i v_i $$

$$ \sum\limits_{i=0}^{n-1} x_i w_i \le C $$

$$ x_i\le m_i,\ \ \ \ i\in\left[0,n-1\right] $$

$$ int\ \ \ x_i,\ \ \ \ \ i\in\left[0,n-1\right] $$

## Graph

### Vertex properties

 - $i$: Integer, basic property
 - $pr$: Integer, remaining weight, basic property
