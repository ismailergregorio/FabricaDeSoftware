<script>
  document.querySelectorAll('.carousel').forEach(carousel => {
    const track = carousel.querySelector('.carousel-track');
    const prevBtn = carousel.querySelector('.carousel-btn.prev');
    const nextBtn = carousel.querySelector('.carousel-btn.next');
    const cardWidth = carousel.querySelector('.carousel-card').getBoundingClientRect().width;
    let index = 0;

    nextBtn.addEventListener('click', () => {
      index++;
      if (index > track.children.length - 3) index = 0; // loop
      track.style.transform = `translateX(-${index * cardWidth}px)`;
    });

    prevBtn.addEventListener('click', () => {
      index--;
      if (index < 0) index = track.children.length - 3;
      track.style.transform = `translateX(-${index * cardWidth}px)`;
    });
  });
</script>
