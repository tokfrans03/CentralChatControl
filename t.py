def luhnDouble(x):
    if 2*x > 9:
        return 2*x - 9
    else:
        return 2*x

# Alternately applies luhnDouble to the reversed list of credit card digits


def luhnMap(li):
    newLi = []
    n = 0
    while n < len(li):
        if n % 2 == 1:
            newLi.append(luhnDouble(li[n]))
        else:
            newLi.append(li[n])
        n += 1
    return newLi

# If the sum of the luhnMap'd list is divisible by 10, return true, otherwise, return false
# Nums == credit card numbers as a base10 number, e.g. 123456789


def luhn(nums):
    # Turns the numbers into a list of numbers
    li = list(map(lambda x: int(x), reversed(list(str(nums)))))
    return sum(luhnMap(li)) % 10 == 0

from tqdm import tqdm
from array import array
nummap = array("L")
lastnum = 0
# for x in tqdm(range(4035501000000008, 4035501000000008 + 10**9)):
#     if luhn(x):
#         nummap.append(x)
#         lastnum = x
    # if x % 10 ** 5 == 0:
    #     print(len(nummap))

# nummap.remove(4035501000000008)
# print(set(nummap))
# print(max(nummap))