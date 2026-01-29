document.addEventListener("DOMContentLoaded", () => {
  const mountEl = document.getElementById("menu");
  if (!mountEl) return;

  const style = document.createElement("style");
  style.innerHTML = `
    #menu{
      width: 180px;
      padding: 6px;
      border-right: 1px solid #ddd;
      background: #FFF7F0;
      display: flex;
      flex-direction: column;
      gap: 6px;
      box-sizing: border-box;
    }

    #menu .menu-box{
      display: flex;
      flex-direction: column;
      gap: 6px;
    }

    #menu .menu-btn{
      width: 100%;
      padding: 6px 6px;
      font-size: 14px;
      border-radius: 8px;
      background: #FFF7F0;
      border: 1px solid #e2d7cf;
      color: #222;
      text-align: left;
      cursor: pointer;
      box-sizing: border-box;
    }

    #menu .menu-btn:hover{
      background: #F6EDE5;
    }

    #menu .submenu{
      display: none;
      margin-left: 10px;
      padding-left: 8px;
      border-left: 2px solid #e2d7cf;
      gap: 6px;
      flex-direction: column;
    }

    #menu .submenu.open{
      display: flex;
    }

    #menu .submenu .menu-btn{
      font-size: 13px;
      padding: 5px 6px;
    }
  `;
  document.head.appendChild(style);

  //這邊增加按鈕
  const menus = [
    {
      text: "上機考測驗區",
      children: [
        { text: "按鈕測試", url: "https://www.google.com/" },
        { text: "按鈕測試", url: "" }
      ]
    }];

  //
  mountEl.innerHTML = `
    <div class="menu-box">
      ${menus
        .map((m, idx) => {
          // 有 children ➜ 可展開
          if (m.children && m.children.length > 0) {
            return `
              <button class="menu-btn parent-btn" type="button" data-index="${idx}">
                ${m.text} ▼
              </button>
              <div class="submenu" id="submenu-${idx}">
                ${m.children
                  .map(
                    (c) => `
                      <button class="menu-btn child-btn" type="button" data-url="${c.url}">
                        ${c.text}
                      </button>
                    `
                  )
                  .join("")}
              </div>
            `;
          }

          // 沒 children ➜ 一般跳轉按鈕
          return `
            <button class="menu-btn child-btn" type="button" data-url="${m.url}">
              ${m.text}
            </button>
          `;
        })
        .join("")}
    </div>
  `;

  //
  mountEl.querySelectorAll(".parent-btn").forEach((btn) => {
    btn.addEventListener("click", () => {
      const idx = btn.getAttribute("data-index");
      const box = document.getElementById(`submenu-${idx}`);
      box.classList.toggle("open");
    });
  });

  //
  mountEl.querySelectorAll(".child-btn").forEach((btn) => {
    btn.addEventListener("click", () => {
      const url = btn.getAttribute("data-url");
      if (url) window.location.href = url;
    });
  });
});
