# import sys
#
# print("Hello, from python script")
# print(sys.argv[1])

import sys
from tube_dl import Youtube

link = sys.argv[1]
# print(link)

yt = Youtube(link)
print(f"Name: {yt.title}")
print(f"Views: {yt.views}")
print(f"Likes: {yt.likes}")
print(f"Dislikes: {yt.dislikes}")
print(f"Thumbnail: {yt.thumbnail}")
