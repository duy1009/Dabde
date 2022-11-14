import cv2
import numpy as np

width = 26
height = 14
scale = 32
px_1_block = 32

sprite = cv2.imread("D:\\Java\\Dabde_v1\\res\\outside_sprites.png")
window = np.ones((height*px_1_block, width*px_1_block, 3),dtype=np.uint8)*0
Map = np.ones((height, width, 3), np.uint8)*0
index = 0

def getAllBlock(img_src, px_1_block):
    lst_img = []
    for i in range(int(img_src.shape[0]/px_1_block)):
        for j in range(int(img_src.shape[1]/px_1_block)):
            im = img_src[i*px_1_block: (i+1)*px_1_block, j*px_1_block: (j+1)*px_1_block]
            lst_img.append(im)
    return lst_img


blocks = getAllBlock(sprite, px_1_block)

def click_event1(event, x, y, flags, params):
    global index
    if event == cv2.EVENT_LBUTTONDOWN:
        index = int(y/px_1_block)*int(sprite.shape[1]/px_1_block)+ int(x/px_1_block)
        print(index)


def click_event2(event, x, y, flags, params):
    if event == cv2.EVENT_LBUTTONDOWN:
        xind = int(y/px_1_block)
        yind = int(x/px_1_block)

        Map[xind, yind, 2] = index
        window[xind*px_1_block:(xind+1)*px_1_block, yind*px_1_block:(yind+1)*px_1_block] = blocks[index]



cv2.imshow("Sprite",sprite)

while True:
    cv2.imshow("Map",window) 
    cv2.imshow("block", blocks[index])
    cv2.setMouseCallback('Sprite', click_event1)
    cv2.setMouseCallback('Map', click_event2)
    if cv2.waitKey(1) == ord("q"):
        break
cv2.imwrite("map_rlt.png", Map)
cv2.destroyAllWindows()