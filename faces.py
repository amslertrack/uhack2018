import numpy as np
import cv2

face_cascade = cv2.CascadeClassifier('cascades/data/haarcascade_frontalface_alt.xml')
eye_cascade = cv2.CascadeClassifier('cascades/data/haarcascade_eye.xml')

cap = cv2.VideoCapture(0)

while(True):
	#capture frame-by-frame
	ret, frame = cap.read()
	gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
	faces = face_cascade.detectMultiScale(gray)
	for (x, y, w, h) in faces:
		print(x, y, w, h)
		roi_gray = gray[y:y+h, x:x+w] #region of interest - gray
		roi_color = frame[y:y+h, x:x+w] #region of interest - color


		"""
		recognize by deep learning e.g. scikit learn
		"""
		img_item = "my-image.png"
		cv2.imwrite(img_item, roi_gray)

		"""
		rectangle
		"""
		color = (255, 0, 0) #BGR
		stroke = 2 #thickness of line
		end_cord_x = x+w #width
		end_cord_y = y+h #height
		cv2.rectangle(frame, (x, y), (end_cord_x, end_cord_y), color, stroke)

	#display the frames
	cv2.imshow('Face Recognition', frame)
	if cv2.waitKey(20) & 0xFF == ord('q'):
		break

cap.release
cv2.destroyAllWindows()
