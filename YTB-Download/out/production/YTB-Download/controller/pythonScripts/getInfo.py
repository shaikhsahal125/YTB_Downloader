
import sys
from tube_dl import Youtube

link = sys.argv[1]
yt = Youtube(link)

print(f"Title: {yt.title}")
print(f'Views: {yt.views}')
print(f'Likes: {yt.likes}')
print(f'Dislikes: {yt.dislikes}')
print(f'Thumbnail: {yt.thumbnail}')
