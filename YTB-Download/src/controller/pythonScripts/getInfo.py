

import sys
from tube_dl import Youtube, extras

link = sys.argv[1] # youtube url to be downloaded
content_type = sys.argv[2] # the type whatever user wants to download (video or audio)
save_file_path = sys.argv[3] # tha file path the user want to save the downloaded file

print("I am here")

yt = Youtube(link)

print("Fetching data...")

print(f"Title: {yt.title}")
print(f'Views: {yt.views}')
print(f'Likes: {yt.likes}')
print(f'Dislikes: {yt.dislikes}')


# path = input("Enter path: ")
# yt.formats.first().download(path = path)
# print("Success")

if (content_type == 'audio'):
    # print(yt.formats.filter_by(only_audio=True).first()) # check it later for this line
    # yt.formats.last().download(path =save_file_path)
    # print(yt.formats.filter_by(only_audio=True)[0])
    first_of_list = yt.formats.filter_by(only_audio=True)[0].download(path = save_file_path)
    extras.Convert(first_of_list, 'mp3', add_meta=True)
else:
    first_of_list = yt.formats.first().download(path = save_file_path)
    extras.Convert(first_of_list, 'mp4', keep_original=True)

print("Successfully downloaded...")
